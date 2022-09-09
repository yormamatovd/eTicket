package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.PaymentReturn;
import ai.ecma.appeticketserver.entity.TicketPayment;
import ai.ecma.appeticketserver.repository.PaymentReturnRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.RefundCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Value(value = "${stripe.secret-key}")
    private String secretKey;


    private final PaymentReturnRepository paymentReturnRepository;

    @Override
    @SneakyThrows
    public Charge payWithStripe(double amount, String token) {
        Stripe.apiKey = secretKey;

        ChargeCreateParams createParams = ChargeCreateParams.builder()
                .setAmount((long) (amount * 100))
                .setCurrency("uzs")
                .setSource(token)
                .build();

        return Charge.create(createParams);
    }

    @Override
    public void refundCharge(double amount, TicketPayment ticketPayment) {

        RefundCreateParams params = RefundCreateParams.builder()
                .setAmount((long) amount * 100)
                .setCharge(ticketPayment.getPayment().getChargeId())
                .build();

        PaymentReturn paymentReturn = new PaymentReturn(
                ticketPayment.getPayment().getCardNumber(),
                ticketPayment.getPayment().getCardType(),
                ticketPayment,
                amount,
                false
        );

        try {
            Stripe.apiKey = secretKey;
            Refund.create(params);
            paymentReturn.setSuccess(true);
        } catch (StripeException e) {
            e.printStackTrace();
        } finally {
            paymentReturnRepository.save(paymentReturn);
        }

    }


}
