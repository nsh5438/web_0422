package kr.hs.dgsw.web01blog.Service;

import javafx.geometry.Pos;
import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Domain.User;
import kr.hs.dgsw.web01blog.Protocol.PostUserPro;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web01blog.Protocol.ResponseType;
import kr.hs.dgsw.web01blog.Repository.AttachmentRep;
import kr.hs.dgsw.web01blog.Repository.PostRep;
import kr.hs.dgsw.web01blog.Repository.UserRep;
import org.omg.PortableServer.POA;
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
    @Autowired
    private AttachmentRep attachmentRep;

    @Override
    public ResponseFormat get(String acccount) {
        Post post = this.postRep.findTopByAccountOrderByCreatedDesc(acccount).orElse(null);
        return new ResponseFormat(ResponseType.POST_GET, post);
    }


    @Override
    public ResponseFormat getCount(String account) {
        List<Post> postList = this.postRep.findAll();
        List<Post> postUserProList = new ArrayList<>();
        postList.forEach(post -> {
            Optional<User> found = this.userRep.findByAccount(account);
            if (found.isPresent()){
                postUserProList.add(post);
            }
        });
        return new ResponseFormat(ResponseType.POST_GET, postUserProList.size());
    }

    @Override
    public ResponseFormat View(Long id) {
        Optional<Post> post = this.postRep.findById(id);
        return new ResponseFormat(ResponseType.POST_GET, post.get());
    }

    @Override
    public ResponseFormat AddPost(Post post) {
        Post addpost = this.postRep.save(post);
        String username = this.userRep.findByAccount(post.getAccount()).map(user -> user.getName()).orElse(null);
        return new ResponseFormat(ResponseType.POST_ADD,new PostUserPro(addpost,username), post.getId());
    }

    @Override
    public ResponseFormat ListPost() {
        List<Post> postList = this.postRep.findAll();
        List<PostUserPro> postUserProList = new ArrayList<>();
        postList.forEach(post -> {
            Optional<User> found = this.userRep.findByAccount(post.getAccount());
            String username = null;
            if (found.isPresent()) username =found.get().getName();
            postUserProList.add(new PostUserPro(post, username));
        });
        return new ResponseFormat(ResponseType.POST_GET, postUserProList);
    }

    @Override
    public ResponseFormat UpdatePost(Long id, Post post) {
        Post updaatepost = this.postRep.findById(id)
                .map(found -> {
                    found.setTitle(Optional.ofNullable(post.getTitle()).orElse(found.getTitle()));
                    found.setContent(Optional.ofNullable(post.getContent()).orElse(found.getContent()));
                    return this.postRep.save(found);
                })
                .orElse(null);
        return new ResponseFormat(ResponseType.POST_UPDATE, updaatepost, id);
    }

    @Override
    public ResponseFormat DeletePost(Long id) {
        Optional<Post> found = this.postRep.findById(id);
        if (found.isPresent()) {
            this.postRep.delete(found.get());
            return new ResponseFormat(ResponseType.POST_DELETE, found, id);
        } else
            return new ResponseFormat(ResponseType.FAIL, found);
    }



}
