package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.TicketPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketPaymentRepository extends JpaRepository<TicketPayment, UUID> {

    TicketPayment findByTicketId(UUID ticket_id);
}
