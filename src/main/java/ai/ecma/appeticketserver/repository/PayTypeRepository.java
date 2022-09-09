package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.PayType;
import ai.ecma.appeticketserver.enums.PayTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PayTypeRepository extends JpaRepository<PayType, UUID> {


    boolean existsByName(PayTypeEnum name);

    Optional<PayType> findByName(PayTypeEnum name);
}
