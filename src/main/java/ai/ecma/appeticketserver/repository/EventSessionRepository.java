package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.EventSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.UUID;

public interface EventSessionRepository extends JpaRepository<EventSession, UUID> {

    @Query(value = "select count(es.id) > 0\n" +
            "from event_sessions es\n" +
            "         join events e on e.id = es.event_id\n" +
            "         join places p on e.place_id = p.id\n" +
            "where p.id = :placeId\n" +
            "  and (es.start_time between :newStartTime and :newEndTime\n" +
            "    or es.end_time between :newStartTime and :newEndTime\n" +
            "    or :newStartTime between es.start_time and es.end_time);", nativeQuery = true)
    boolean eventSessionTimeIsDuplicate(@Param("placeId") UUID placeId,
                                        @Param("newStartTime") Timestamp newStartTime,
                                        @Param("newEndTime") Timestamp newEndTime);
}
