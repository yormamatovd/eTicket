package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.EventSession;
import ai.ecma.appeticketserver.entity.TemplateChair;
import ai.ecma.appeticketserver.entity.Ticket;
import ai.ecma.appeticketserver.entity.User;
import ai.ecma.appeticketserver.payload.ApiResult;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    Ticket chairToTicket(TemplateChair chair, EventSession eventSession);

    List<Ticket> chairsToTickets(List<TemplateChair> chairs, EventSession eventSession);

    ApiResult<?> cancel(User user, UUID ticketId);
}
