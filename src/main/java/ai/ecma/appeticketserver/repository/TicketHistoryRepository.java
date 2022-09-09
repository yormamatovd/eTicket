package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.TicketHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository(value = "TicketHistoryRepository")
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, UUID> {

    Page<TicketHistory> getAllByTicketId(UUID ticket_id, Pageable pageable);


}
