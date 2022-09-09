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
@Table(name = "template_chairs")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update template_chairs set deleted=true where id=?")
public class TemplateChair extends AbsEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private SeatTemplate template;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "row", nullable = false)
    private String row;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;
}
