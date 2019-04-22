package kr.hs.dgsw.web01blog.Controller;

import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Protocol.PostUserPro;
import kr.hs.dgsw.web01blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/addpost")
    public PostUserPro AddPost(@RequestBody Post post) { return this.postService.AddPost(post); }

    @GetMapping("/listpost")
    public List<PostUserPro> ListPost() { return this.postService.ListPost(); }

    @PutMapping("/updatepost/{id}")
    public Post UpdatePost(@PathVariable Long id, @RequestBody Post post) { return this.postService.UpdatePost(id,post);}

    @DeleteMapping("/deletepost/{id}")
    public boolean DeletePost(@PathVariable Long id) {return this.postService.DeletePost(id);}
}
