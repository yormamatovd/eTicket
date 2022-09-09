package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.PaymentReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentReturnRepository extends JpaRepository<PaymentReturn, UUID> {

    List<PaymentReturn> findAllBySuccessFalse();
}
