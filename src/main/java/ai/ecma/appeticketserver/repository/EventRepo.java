package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepo extends JpaRepository<Event, UUID> {


    Page<Event>findAllByCategoryId(UUID category_id, Pageable pageable);

    @Query(value = "select e.* from events e\n" +
            "join event_sessions es on e.id = es.event_id and es.deleted=false\n" +
            "where e.deleted =false and es.id =:sessionsId", nativeQuery = true)
    Event findSessionsId(@Param("sessionsId") UUID sessionsId);
}
