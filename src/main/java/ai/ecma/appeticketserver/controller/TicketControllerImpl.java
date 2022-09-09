package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.entity.User;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TicketControllerImpl implements TicketController{

    private final TicketService ticketService;

    @Override
    public ApiResult<?> cancel(User user, UUID ticketId) {
        return ticketService.cancel(user, ticketId);
    }
}
