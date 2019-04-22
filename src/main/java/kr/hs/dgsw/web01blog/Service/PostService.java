package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Protocol.PostUserPro;

import java.util.List;

public interface PostService {
    PostUserPro AddPost(Post post);

    List<PostUserPro> ListPost();

    Post UpdatePost(Long id, Post post);

    boolean DeletePost(Long id);
}
