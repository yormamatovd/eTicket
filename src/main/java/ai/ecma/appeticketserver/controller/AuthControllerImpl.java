package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.TokenDto;
import ai.ecma.appeticketserver.payload.req.LoginDto;
import ai.ecma.appeticketserver.payload.req.PhoneNumberReqDto;
import ai.ecma.appeticketserver.payload.req.PhoneVerifyReqDto;
import ai.ecma.appeticketserver.payload.req.RegisterReqDto;
import ai.ecma.appeticketserver.service.AuthService;
import ai.ecma.appeticketserver.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ApiResult<?> checkPhone(PhoneNumberReqDto dto) {
       return authService.checkPhone(dto);
    }

    @Override
    public ApiResult<?> verify(PhoneVerifyReqDto dto) {
        return authService.verify(dto);
    }

    @Override
    public ApiResult<?> signUp(RegisterReqDto dto) {
        return authService.signUp(dto);
    }

    @Override
    public ApiResult<TokenDto> refreshToken(TokenDto dto) {
        return authService.refreshToken(dto);
    }

    @Override
    public ApiResult<TokenDto> login(LoginDto loginDto) {
        return authService.login(loginDto);

    }
}
