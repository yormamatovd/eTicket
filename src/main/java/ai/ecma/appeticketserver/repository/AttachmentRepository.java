package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository(value = "attachmentRepository")
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

    Optional<Attachment> findByName(String name);
}
