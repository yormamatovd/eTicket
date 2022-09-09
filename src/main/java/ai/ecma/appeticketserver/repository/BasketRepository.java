package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {

    @Transactional
    @Modifying
    @Query(value = "update tickets set basket_id = null where basket_id = (select id from baskets where expire_time > :currentTime);", nativeQuery = true)
    void clearBasket(@Param("currentTime") Timestamp currentTime);

    Optional<Basket> findByUserId(UUID user_id);
}
