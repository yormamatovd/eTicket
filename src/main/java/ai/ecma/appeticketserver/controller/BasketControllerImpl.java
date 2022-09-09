package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.entity.User;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class BasketControllerImpl implements BasketController{
    private final BasketService basketService;

    @Override
    public ApiResult<?> addTicket(UUID ticketId, User user) {
        return basketService.addTicket(ticketId, user);
    }

    @Override
    public ApiResult<?> removeTicket(UUID ticketId, User user) {
        return basketService.removeTicket(ticketId, user);
    }

    @Override
    public ApiResult<?> clearBasket(User user) {
        return basketService.clearBasket(user);
    }

    @Override
    public ApiResult<?> buy(User user, String cardToken) {
        return basketService.buy(user, cardToken);
    }
}
