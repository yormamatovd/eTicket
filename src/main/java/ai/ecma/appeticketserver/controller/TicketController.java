package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.annotation.CurrentUser;
import ai.ecma.appeticketserver.entity.User;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping(TicketController.TICKET_CONTROLLER)
public interface TicketController {

    String TICKET_CONTROLLER = AppConstant.BASE_PATH + "/ticket";

    @PostMapping("/cancel/{ticketId}")
    ApiResult<?> cancel(@CurrentUser User user, @PathVariable UUID ticketId);
}
