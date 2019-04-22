package kr.hs.dgsw.web01blog.Protocol;

import kr.hs.dgsw.web01blog.Domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostUserPro extends Post {

    private String username;

    public PostUserPro(Post post, String username) {
        super(post);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
