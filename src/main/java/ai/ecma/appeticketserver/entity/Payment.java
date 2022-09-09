package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import ai.ecma.appeticketserver.enums.CardTypeEnum;
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
@Table(name = "payments")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update payments set deleted=true where id=?")
public class Payment extends AbsEntity {

    @Column(name = "total_sum", nullable = false)
    private Double totalSum;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_type", nullable = false)
    private String cardType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_type_id")
    private PayType payType;

    @OneToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

    private String chargeId;
}
