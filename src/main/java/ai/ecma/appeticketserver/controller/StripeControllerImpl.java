package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CardDto;
import ai.ecma.appeticketserver.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StripeControllerImpl implements StripeController{
    private final StripeService stripeService;

    @Override
    public ApiResult<?> createToken(CardDto cardDto) {
        return stripeService.createToken(cardDto);
    }
}
