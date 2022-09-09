package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.*;
import ai.ecma.appeticketserver.enums.TicketStatusEnum;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.repository.*;
import ai.ecma.appeticketserver.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ReturnTariffRepository returnTariffRepository;
    private final EventRepo eventRepo;
    private final PaymentRepository paymentRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketPaymentRepository ticketPaymentRepository;
    private final PaymentService paymentService;


    @Override
    public Ticket chairToTicket(TemplateChair chair, EventSession eventSession) {
        return new Ticket(
                chair.getName(), eventSession, chair.getSector(), chair.getRow(), chair.getPrice(), chair.getStatus(), null
        );
    }

    @Override
    public List<Ticket> chairsToTickets(List<TemplateChair> chairs, EventSession eventSession) {
        return chairs.stream().map(chair -> chairToTicket(chair, eventSession)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ApiResult<?> cancel(User user, UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> RestException.notFound(MessageService.getMessage("TICKET_NOT_FOUND")));
        EventSession eventSession = ticket.getEventSession();
        Event event = eventRepo.findSessionsId(eventSession.getId());
        if (eventSession.getStartTime().after(Timestamp.valueOf(LocalDateTime.now())))
            throw RestException.restThrow(MessageService.getMessage("EVENT_SESSION_STARTED"), HttpStatus.BAD_REQUEST);

        if (!ticket.getStatus().equals(TicketStatusEnum.SOLD)) {
            throw RestException.restThrow(MessageService.getMessage("TICKET_IS_NOT_SOLD"), HttpStatus.BAD_REQUEST);
        }

        Payment payment = paymentRepository.findByTicketId(ticket.getId());
        if (!payment.getUser().getId().equals(user.getId())) {
            throw RestException.restThrow(MessageService.getMessage("THIS_TICKET_NOT_YOURS"), HttpStatus.FORBIDDEN);
        }

        List<ReturnTariff> tariffList = returnTariffRepository.findAllByEventIdOrderByToTimeDesc(event.getId());


        TicketPayment ticketPayment = ticketPaymentRepository.findByTicketId(ticket.getId());

        if (event.getHasReturning() || !tariffList.isEmpty()) {

            long diffHours = CommonUtils.compareTwoTimeStamps(eventSession.getStartTime(), new Timestamp(System.currentTimeMillis()));

            Optional<ReturnTariff> optionalTariff = tariffList.stream().filter(returnTariff -> (long) returnTariff.getToTime() < diffHours).findFirst();
            if (optionalTariff.isPresent()) {
                Double price = ticket.getPrice();
                ReturnTariff returnTariff = optionalTariff.get();

                double returnPrice = (price / 100) * returnTariff.getPercent();

                paymentService.refundCharge(returnPrice, ticketPayment);

            }

        }

        ticket.setStatus(TicketStatusEnum.AVAILABLE);
        TicketHistory ticketHistory = new TicketHistory();
        ticketHistory.setTicket(ticket);
        ticketHistory.setStatus(TicketStatusEnum.AVAILABLE);
        ticketHistoryRepository.save(ticketHistory);

        ticketPayment.setCancelled(true);
        ticketPaymentRepository.save(ticketPayment);

        return ApiResult.successResponse("TICKET_SUCCESS_CANCELED");
    }
}
