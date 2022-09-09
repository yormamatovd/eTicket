package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import ai.ecma.appeticketserver.enums.PayTypeEnum;
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
@Table(name = "pay_types")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update pay_types set deleted=true where id=?")
public class PayType extends AbsEntity {

    @Column(nullable = false, name = "name")
    @Enumerated(EnumType.STRING)
    private PayTypeEnum name;

    private boolean active;
}
