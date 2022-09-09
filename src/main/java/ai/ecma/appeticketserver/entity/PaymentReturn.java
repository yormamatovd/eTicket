package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_return")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update payment_return set deleted=true where id=?")
public class PaymentReturn extends AbsEntity {

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_type", nullable = false)
    private String cardType;

    @OneToOne
    @JoinColumn(name = "ticket_payment_id")
    private TicketPayment ticketPayment;

    @Column(name = "amount_refunding", nullable = false)
    private Double amountRefunding;

    private boolean success;
}
