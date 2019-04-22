package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Domain.User;
import kr.hs.dgsw.web01blog.Protocol.PostUserPro;
import kr.hs.dgsw.web01blog.Repository.PostRep;
import kr.hs.dgsw.web01blog.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceimp implements PostService {

    @Autowired
    private PostRep postRep;

    @Autowired
    private UserRep userRep;

    @Override
    public PostUserPro AddPost(Post post) {
        Post addpost = this.postRep.save(post);
        String username = this.userRep.findByAccount(post.getAccount()).map(user -> user.getName()).orElse(null);
        return new PostUserPro(addpost,username);
    }

    @Override
    public List<PostUserPro> ListPost() {
        List<Post> postList = this.postRep.findAll();
        List<PostUserPro> postUserProList = new ArrayList<>();
        postList.forEach(post -> {
            Optional<User> found = this.userRep.findByAccount(post.getAccount());
            String username = null;
            if (found.isPresent()) username =found.get().getName();
            postUserProList.add(new PostUserPro(post, username));
        });
        return postUserProList;
    }

    @Override
    public Post UpdatePost(Long id, Post post) {
        return this.postRep.findById(id)
                .map(found -> {
                    found.setContent(Optional.ofNullable(post.getContent()).orElse(found.getContent()));
                    return this.postRep.save(found);
                })
                .orElse(null);
    }

    @Override
    public boolean DeletePost(Long id) {
        Optional<Post> found = this.postRep.findById(id);
        if (found.isPresent()) {
            this.postRep.delete(found.get());
            return true;
        } else
            return false;
    }


}
