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
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket_histories")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update ticket_histories set deleted=true where id=?")
public class TicketHistory extends AbsEntity {

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "action_time", nullable = false)
    private Timestamp actionTime = new Timestamp(System.currentTimeMillis());

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;

    public TicketHistory(Ticket ticket, TicketStatusEnum status) {
        this.ticket = ticket;
        this.status = status;
    }
}
