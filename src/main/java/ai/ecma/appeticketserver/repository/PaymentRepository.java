package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query(value = "select p.* " +
            "from payments p " +
            "join ticket_payments tp on p.id = tp.payment_id and tp.deleted=false " +
            "join tickets t on t.id = tp.ticket_id and t.deleted=false " +
            "where p.deleted = false and t.id =:ticketId", nativeQuery = true)
    Payment findByTicketId(@Param("ticketId") UUID ticketId);
}
