package kr.hs.dgsw.web01blog.Controller;

import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Protocol.PostUserPro;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web01blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/get/{account}")
    public ResponseFormat GetPost(@PathVariable String account) {return this.postService.get(account);}

    @GetMapping("/getcount/{account}")
    public ResponseFormat GetCount(@PathVariable String account) {return this.postService.getCount(account);}

    @PostMapping("/addpost")
    public ResponseFormat AddPost(@RequestBody Post post) { return this.postService.AddPost(post); }

    @GetMapping("/listpost")
    public ResponseFormat ListPost() { return this.postService.ListPost(); }

    @GetMapping("/view/{id}")
    public ResponseFormat View(@PathVariable Long id) {return  this.postService.View(id);}

    @PutMapping("/updatepost/{id}")
    public ResponseFormat UpdatePost(@PathVariable Long id, @RequestBody Post post) { return this.postService.UpdatePost(id,post);}

    @DeleteMapping("/deletepost/{id}")
    public ResponseFormat DeletePost(@PathVariable Long id) {return this.postService.DeletePost(id);}
}
