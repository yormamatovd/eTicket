package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CardDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(StripeController.STRIPE_CONTROLLER)
public interface StripeController {
    String STRIPE_CONTROLLER = AppConstant.BASE_PATH + "/stripe";

    @PostMapping
    ApiResult<?> createToken(@RequestBody @Valid CardDto cardDto);
}
