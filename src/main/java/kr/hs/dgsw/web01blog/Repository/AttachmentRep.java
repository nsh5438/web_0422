package kr.hs.dgsw.web01blog.Repository;

import kr.hs.dgsw.web01blog.Domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRep extends JpaRepository<Attachment, Long> {

    Optional<Attachment> findByPostID(Long postID);
}
