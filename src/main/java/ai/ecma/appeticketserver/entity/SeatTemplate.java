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
@Table(name = "seat_templates")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update seat_templates set deleted=true where id=?")
public class SeatTemplate extends AbsEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(optional = false)
    @JoinColumn(name = "schema_id")
    private Attachment schema;
}
