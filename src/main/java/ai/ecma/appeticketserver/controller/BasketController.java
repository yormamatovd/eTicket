package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.annotation.CurrentUser;
import ai.ecma.appeticketserver.entity.User;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping(BasketController.BASKET_CONTROLLER)
public interface BasketController {
    String BASKET_CONTROLLER = AppConstant.BASE_PATH + "/basket";

    @PostMapping("/add/ticket")
    ApiResult<?> addTicket(@RequestParam UUID ticketId, @CurrentUser User user);

    @PostMapping("/remove/ticket")
    ApiResult<?> removeTicket(@RequestParam UUID ticketId, @CurrentUser User user);

    @PostMapping("/clear")
    ApiResult<?> clearBasket(@CurrentUser User user);

    @PostMapping("/buy")
    ApiResult<?> buy(@CurrentUser User user, @RequestHeader String cardToken);
}
