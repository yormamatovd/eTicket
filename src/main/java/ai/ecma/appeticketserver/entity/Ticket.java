package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import ai.ecma.appeticketserver.enums.TicketStatusEnum;
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
@Table(name = "tickets")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update tickets set deleted=true where id=?")
public class Ticket extends AbsEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_session_id")
    private EventSession eventSession;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "row", nullable = false)
    private String row;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;
}
