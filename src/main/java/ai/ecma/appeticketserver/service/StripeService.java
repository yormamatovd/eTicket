package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CardDto;

public interface StripeService {
    ApiResult<?> createToken(CardDto cardDto);

}
