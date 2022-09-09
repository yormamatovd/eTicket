package ai.ecma.appeticketserver.common;

import ai.ecma.appeticketserver.entity.PaymentReturn;
import ai.ecma.appeticketserver.repository.BasketRepository;
import ai.ecma.appeticketserver.repository.PaymentReturnRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class ScheduledComponent {
    @Value(value = "${stripe.secret-key}")
    private String secretKey;

    private final BasketRepository basketRepository;
    private final PaymentReturnRepository paymentReturnRepository;

    @Scheduled(fixedRate = 60000)
    public void clearBasket() {
        basketRepository.clearBasket(new Timestamp(System.currentTimeMillis()));
    }

//    @Scheduled(fixedRate = 5000)
//    @SneakyThrows
//    public void fixedDelay(){
//        System.out.println("Kod boshlandi: "+new Timestamp(System.currentTimeMillis()));
//        long currentTimeMillis = System.currentTimeMillis();
//        while (System.currentTimeMillis() - currentTimeMillis < 3000){
//            System.out.print("");
//        }
//        System.out.println("Uyg'ondi "+ new Timestamp(System.currentTimeMillis()));
//    }


    @Scheduled(fixedRate = 1000 * 60)
    public void refundUnSuccess() {
        List<PaymentReturn> allSuccessFalse = paymentReturnRepository.findAllBySuccessFalse();

        for (PaymentReturn paymentReturn : allSuccessFalse) {

            RefundCreateParams params = RefundCreateParams.builder()
                    .setAmount((long) (paymentReturn.getAmountRefunding() * 100))
                    .setCharge(paymentReturn.getTicketPayment().getPayment().getChargeId())
                    .build();

            try {
                Stripe.apiKey = secretKey;
                Refund refund = Refund.create(params);
                paymentReturn.setSuccess(true);
                paymentReturnRepository.save(paymentReturn);
            } catch (StripeException e) {
                e.printStackTrace();
            }
        }
    }
}
