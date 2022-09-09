package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.*;
import ai.ecma.appeticketserver.enums.PayTypeEnum;
import ai.ecma.appeticketserver.enums.TicketStatusEnum;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.repository.*;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final TicketRepository ticketRepository;
    private final PaymentService paymentService;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final PayTypeRepository payTypeRepository;
    private final PaymentRepository paymentRepository;
    private final TicketPaymentRepository ticketPaymentRepository;

    @Value("${basket.live-time}")
    private long expiration;

    @Override
    public ApiResult<?> addTicket(UUID ticketId, User user) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> RestException.notFound(MessageService.getMessage("TICKET_NOT_FOUND")));
        Basket basket = basketRepository.findByUserId(user.getId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("BASKET_NOT_FOUND")));

        if (!ticket.getStatus().equals(TicketStatusEnum.AVAILABLE))
            throw new RestException("Ticket is not available", HttpStatus.CONFLICT);

        ticket.setBasket(basket);
        ticket.setStatus(TicketStatusEnum.BOOKED);
        basket.setExpireTime(new Timestamp(System.currentTimeMillis() + expiration));


        basketRepository.save(basket);
        ticketRepository.save(ticket);
        return ApiResult.successResponse(MessageService.getMessage("TICKET_SAVED"));
    }

    @Override
    public ApiResult<?> removeTicket(UUID ticketId, User user) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> RestException.notFound(MessageService.getMessage("TICKET_NOT_FOUND")));
        Basket basket = basketRepository.findByUserId(user.getId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("BASKET_NOT_FOUND")));

        if (ticket.getBasket().getId().equals(basket.getId())) {
            ticket.setBasket(null);
            ticket.setStatus(TicketStatusEnum.AVAILABLE);
        }

        if (basketIsEmpty(basket))
            basket.setExpireTime(null);

        ticketRepository.save(ticket);
        basketRepository.save(basket);
        return ApiResult.successResponse(MessageService.getMessage("TICKET_DELETED"));
    }

    @Override
    public ApiResult<?> clearBasket(User user) {
        Basket basket = basketRepository.findByUserId(user.getId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("BASKET_NOT_FOUND")));

        List<Ticket> tickets = ticketRepository.findAllByBasketId(basket.getId());
        tickets.forEach(ticket -> {
            ticket.setBasket(null);
            ticket.setStatus(TicketStatusEnum.AVAILABLE);
        });

        basket.setExpireTime(null);

        ticketRepository.saveAll(tickets);
        basketRepository.save(basket);
        return ApiResult.successResponse(MessageService.getMessage("TICKETS_SAVED"));
    }

    @Override
    public ApiResult<?> buy(User user, String cardToken) {
        Optional<Basket> optionalBasket = basketRepository.findByUserId(user.getId());

        if (optionalBasket.isEmpty() || basketIsEmpty(optionalBasket.get()))
            throw RestException.restThrow(MessageService.getMessage("BASKET_IS_EMPTY"), HttpStatus.BAD_REQUEST);

        Basket basket = optionalBasket.get();

        List<Ticket> tickets = ticketRepository.findAllByBasketId(basket.getId());
        double sum = tickets.stream().mapToDouble(Ticket::getPrice).sum();

        PayType payType = payTypeRepository.findByName(PayTypeEnum.STRIPE).orElseThrow(() -> RestException.notFound("PAY_TYPE_NOT_FOUND"));

        Charge charge = paymentService.payWithStripe(sum, cardToken);

        Payment payment = new Payment(
                sum,
                charge.getPaymentMethodDetails().getCard().getLast4(),
                charge.getPaymentMethodDetails().getCard().getBrand(),
                user,
                payType,
                null,
                charge.getId()
        );

        paymentRepository.save(payment);

        List<TicketHistory> histories = new ArrayList<>();
        List<TicketPayment> ticketPayments = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticket.setStatus(TicketStatusEnum.SOLD);
            ticket.setBasket(null);
            histories.add(new TicketHistory(ticket, TicketStatusEnum.SOLD));
            ticketPayments.add(new TicketPayment(ticket, false, ticket.getPrice(), payment));
        }

        ticketRepository.saveAll(tickets);
        ticketHistoryRepository.saveAll(histories);
        ticketPaymentRepository.saveAll(ticketPayments);

        return ApiResult.successResponse(MessageService.getMessage("SUCCESS_PAYMENT"));
    }

    public boolean basketIsEmpty(Basket basket) {
        return !ticketRepository.existsByBasketId(basket.getId());
    }
}
