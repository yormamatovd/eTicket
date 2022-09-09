package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaceRepo extends JpaRepository<Place, UUID> {
}
