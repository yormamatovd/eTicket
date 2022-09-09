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
@Table(name = "return_tariffs")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update return_tariffs set deleted=true where id=?")
public class ReturnTariff extends AbsEntity {

    @Column(name = "to_time", nullable = false)
    private Integer toTime;//soat

    @Column(name = "percent", nullable = false)
    private Double percent;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    private boolean active;
}
