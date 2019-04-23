package kr.hs.dgsw.web01blog.Repository;

import kr.hs.dgsw.web01blog.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRep extends JpaRepository<Post, Long> {

    Optional<Post> findTopByAccountOrderByAccountDesc(String account);

    public Optional<Post> findByAccount(String account);
}
