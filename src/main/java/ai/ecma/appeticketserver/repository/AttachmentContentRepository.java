package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository(value = "attachmentContentRepository")
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {

    Optional<AttachmentContent> findByAttachmentName(String attachment_name);

    Optional<AttachmentContent> findByAttachmentId(UUID attachment_id);
}
