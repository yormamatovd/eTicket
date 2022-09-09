package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.Ticket;
import ai.ecma.appeticketserver.entity.TicketHistory;
import ai.ecma.appeticketserver.payload.resp.TicketHistoryRespDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository(value = "TicketRepository")
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findAllByBasketId(UUID basket_id);

    boolean existsByBasketId(UUID basket_id);



}
