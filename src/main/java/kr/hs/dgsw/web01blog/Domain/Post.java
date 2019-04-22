package kr.hs.dgsw.web01blog.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String account;

    @Column(nullable = false)
    private String content;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String filepath;

    private String filename;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updated;

    public Post(Post post) {
        this.id = post.getId();
        this.account = post.getAccount();
        this.content = post.getContent();
        this.filepath = post.getFilepath();
        this.filename = post.getFilename();
        this.created = post.getCreated();
        this.updated = post.getUpdated();
    }
}
