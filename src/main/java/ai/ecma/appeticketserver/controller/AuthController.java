package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.TokenDto;
import ai.ecma.appeticketserver.payload.req.LoginDto;
import ai.ecma.appeticketserver.payload.req.PhoneVerifyReqDto;
import ai.ecma.appeticketserver.payload.req.PhoneNumberReqDto;
import ai.ecma.appeticketserver.payload.req.RegisterReqDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(AuthController.AUTH_CONTROLLER)
public interface AuthController {

    String AUTH_CONTROLLER = AppConstant.BASE_PATH + "/auth";

    @PostMapping("/check-phone")
    ApiResult<?> checkPhone(@RequestBody @Valid PhoneNumberReqDto dto);

    @PostMapping("/verify")
    ApiResult<?> verify(@RequestBody @Valid PhoneVerifyReqDto dto);

    @PostMapping("/sign-up")
    ApiResult<?> signUp(@RequestBody @Valid RegisterReqDto dto);

    @PostMapping("/refresh-token")
    ApiResult<TokenDto> refreshToken(@RequestBody @Valid TokenDto dto);

    @PostMapping("/login")
    ApiResult<TokenDto> login(@RequestBody @Valid LoginDto loginDto);



}

