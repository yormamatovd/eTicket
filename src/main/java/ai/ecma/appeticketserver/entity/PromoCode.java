package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "promo_codes")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update promo_codes set deleted=true where id=?")
public class PromoCode extends AbsEntity {

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "percent", nullable = false)
    private Double percent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @Column(name = "expire_date", nullable = false)
    private Timestamp expireDate;
}
