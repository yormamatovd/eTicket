package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode , UUID> {
    boolean existsPromoCodesByUserId(UUID user_id);
}
