package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "artists")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update artists set deleted=true where id=?")
public class Artist extends AbsEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Attachment photo;
}
