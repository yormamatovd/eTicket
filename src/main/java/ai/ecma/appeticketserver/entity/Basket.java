package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "baskets")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update baskets set deleted=true where id=?")
public class Basket extends AbsEntity {

    @OneToOne(optional = false)
    private User user;

    @Column(name = "expire_time")
    private Timestamp expireTime;
}
