package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Protocol.PostUserPro;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;

import java.util.List;

public interface PostService {

    ResponseFormat get(String acccount);

    ResponseFormat AddPost(Post post);

    ResponseFormat ListPost();

    ResponseFormat UpdatePost(Long id, Post post);

    ResponseFormat DeletePost(Long id);
}
