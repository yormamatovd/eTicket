package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Role;
import ai.ecma.appeticketserver.entity.User;
import ai.ecma.appeticketserver.entity.VerificationCode;
import ai.ecma.appeticketserver.enums.RoleEnum;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.TokenDto;
import ai.ecma.appeticketserver.payload.req.LoginDto;
import ai.ecma.appeticketserver.payload.req.PhoneNumberReqDto;
import ai.ecma.appeticketserver.payload.req.PhoneVerifyReqDto;
import ai.ecma.appeticketserver.payload.req.RegisterReqDto;
import ai.ecma.appeticketserver.payload.resp.VerificationRespDto;
import ai.ecma.appeticketserver.repository.RoleRepository;
import ai.ecma.appeticketserver.repository.UserRepository;
import ai.ecma.appeticketserver.repository.VerificationCodeRepository;
import ai.ecma.appeticketserver.security.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${verification-code.expire-time}")
    private Long verificationExpireTime;

    @Value("${verification-code.limit}")
    private Integer limit;

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final RoleRepository roleRepository;
    private final SmsService smsService;
    @Autowired
     AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(username).orElseThrow(RestException::forbidden);
    }

    @Override
    public ApiResult<?> checkPhone(PhoneNumberReqDto dto) throws RestException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - verificationExpireTime);
        List<VerificationCode> verificationCodes = verificationCodeRepository.findAllByCreatedAtAfterOrderByCreatedAt(timestamp);

        if (verificationCodes.size() >= limit) {
            throw RestException.restThrow(MessageService.getMessage("MESSAGE_NOT_ENOUGH_LIMIT"), HttpStatus.BAD_REQUEST);
        }

        if (!verificationCodes.isEmpty()) {
            VerificationCode lastVerificationCode = verificationCodes.get(0);
            if (!lastVerificationCode.isUsed() && lastVerificationCode.getExpireTime().after(new Timestamp(System.currentTimeMillis()))) {
                throw RestException.restThrow(MessageService.getMessage("LAST_VERIFICATION_CODE_NOT_EXPIRED"), HttpStatus.BAD_REQUEST);
            }
        }

        String verificationCode = generateCode();
        smsService.sendMessage(dto.getPhoneNumber(), verificationCode);

        verificationCodeRepository.save(new VerificationCode(dto.getPhoneNumber(), verificationCode));
        return ApiResult.successResponse(MessageService.getMessage("SMS_SENT"));
    }

    @Override
    public ApiResult<?> verify(PhoneVerifyReqDto dto) {
        VerificationCode verificationCode = verificationCodeRepository.checkVerificationCode(dto.getPhoneNumber(), dto.getVerificationCode(), new Timestamp(System.currentTimeMillis()))
                .orElseThrow(() -> RestException.restThrow(MessageService.getMessage("VERIFICATION_CODE_NOT_AVAILABLE"), HttpStatus.BAD_REQUEST));

        Optional<User> optionalUser = userRepository.findByPhoneNumber(dto.getPhoneNumber());
        if (optionalUser.isPresent()) {
            String accessToken = jwtProvider.generateToken(dto.getPhoneNumber(), true);
            String refreshToken = jwtProvider.generateToken(dto.getPhoneNumber(), false);
            verificationCode.setUsed(true);
            verificationCodeRepository.save(verificationCode);
            return ApiResult.successResponse(new VerificationRespDto(accessToken, refreshToken));
        } else {

            return ApiResult.successResponse(new VerificationRespDto());
        }
    }

    @Override
    public ApiResult<?> signUp(RegisterReqDto dto) {
        VerificationCode verificationCode = verificationCodeRepository.checkVerificationCode(dto.getPhoneNumber(), dto.getVerificationCode(), new Timestamp(System.currentTimeMillis()))
                .orElseThrow(() -> RestException.restThrow(MessageService.getMessage("VERIFICATION_CODE_NOT_AVAILABLE"), HttpStatus.BAD_REQUEST));

        Role roleUser = roleRepository.findByType(RoleEnum.ROLE_USER)
                .orElseThrow(() -> RestException.restThrow(MessageService.getMessage("ROLE_NOT_FOUND"), HttpStatus.NOT_FOUND));

        User user = new User(dto.getFirstName(), dto.getLastName(), dto.getPhoneNumber(), roleUser);
        userRepository.save(user);

        verificationCode.setUsed(true);
        verificationCodeRepository.save(verificationCode);

        String accessToken = jwtProvider.generateToken(dto.getPhoneNumber(), true);
        String refreshToken = jwtProvider.generateToken(dto.getPhoneNumber(), false);

        return ApiResult.successResponse(new VerificationRespDto(accessToken, refreshToken));
    }

    @Override
    public ApiResult<TokenDto> refreshToken(TokenDto dto) {
        try {
            jwtProvider.validateToken(dto.getAccessToken());
            return ApiResult.successResponse(dto);
        } catch (ExpiredJwtException exception) {
            Claims claims = exception.getClaims();
            String subject = claims.getSubject();

            try {
                jwtProvider.validateToken(dto.getRefreshToken());
                String username = jwtProvider.getUsername(dto.getRefreshToken());
                if (!username.equals(subject)) {
                    throw RestException.forbidden();
                }
                String accessToken = jwtProvider.generateToken(username, true);
                String refreshToken = jwtProvider.generateToken(username, false);
                return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
            } catch (Exception e) {
                throw RestException.forbidden();
            }
        } catch (Exception e) {
            throw RestException.forbidden();
        }

    }

    @Override
    public ApiResult<TokenDto> login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String accessToken = jwtProvider.generateToken(user.getPhoneNumber(), true);
            String refreshToken = jwtProvider.generateToken(user.getPhoneNumber(), false);
            return ApiResult.successResponse(new TokenDto(accessToken,refreshToken));
        } catch (Exception e) {
            throw RestException.restThrow(MessageService.getMessage("WRONG_USERNAME_OR_PASSWORD"),HttpStatus.FORBIDDEN);
        }
    }

    public String generateCode() {
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            verificationCode.append((int) (Math.random() * 10));
        }
        return verificationCode.toString();
    }
}
