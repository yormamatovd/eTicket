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
@Table(name = "events")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update events set deleted=true where id=?")
public class Event extends AbsEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToOne
    @JoinColumn(name = "banner_id", nullable = false)
    private Attachment banner;

    @Column(name = "has_returning", nullable = false)
    private Boolean hasReturning;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "schema_id", nullable = false)
    private Attachment schema;

    public static Event editEvent(Event old, String name, Place place, String description, Attachment banner, boolean hasReturning, Category category, Attachment schema){
        old.setName(name);
        old.setPlace(place);
        old.setDescription(description);
        old.setBanner(banner);
        old.setHasReturning(hasReturning);
        old.setCategory(category);
        old.setSchema(schema);
        return old;
    }
}
