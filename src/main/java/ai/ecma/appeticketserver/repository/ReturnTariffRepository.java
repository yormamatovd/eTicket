package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.ReturnTariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReturnTariffRepository extends JpaRepository<ReturnTariff, UUID> {

    List<ReturnTariff> findAllByEventIdOrderByToTimeDesc(UUID event_id);

    boolean existsByEventIdAndToTime(UUID event_id, Integer toTime);

    boolean existsByEventIdAndToTimeAndIdNot(UUID event_id, Integer toTime, UUID id);

    boolean existsByEventId(UUID event_id);
}
