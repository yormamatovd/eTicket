package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.ArtistEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistEventRepository extends JpaRepository<ArtistEvent, UUID> {
}
