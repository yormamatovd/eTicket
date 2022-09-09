package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.TokenDto;
import ai.ecma.appeticketserver.payload.req.LoginDto;
import ai.ecma.appeticketserver.payload.req.PhoneNumberReqDto;
import ai.ecma.appeticketserver.payload.req.PhoneVerifyReqDto;
import ai.ecma.appeticketserver.payload.req.RegisterReqDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    ApiResult<?> checkPhone(PhoneNumberReqDto dto);

    ApiResult<?> verify(PhoneVerifyReqDto dto);

    ApiResult<?> signUp(RegisterReqDto dto);

    ApiResult<TokenDto> refreshToken(TokenDto dto);

    ApiResult<TokenDto> login(LoginDto loginDto);
}


