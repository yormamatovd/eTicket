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
@Table(name = "event_sessions")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update event_sessions set deleted=true where id=?")
public class EventSession extends AbsEntity {

    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public static EventSession editSession(EventSession edit, Timestamp startTime, Timestamp endTime, Event event){
        edit.setStartTime(startTime);
        edit.setEndTime(endTime);
        edit.setEvent(event);
        return edit;
    }
}
