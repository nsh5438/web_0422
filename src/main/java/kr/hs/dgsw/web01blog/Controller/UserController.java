package kr.hs.dgsw.web01blog.Controller;

import kr.hs.dgsw.web01blog.Domain.User;
import kr.hs.dgsw.web01blog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/adduser")
    public User AddUser(@RequestBody User user){ return  this.userService.AddUser(user); }

    @GetMapping("/listuser")
    public List<User> ListUser() { return this.userService.ListUser(); }

    @PutMapping("/updateuser/{account}")
    public User UpdateUser(@PathVariable String account, @RequestBody User user){ return this.userService.UpdateUser(account,user); }

    @DeleteMapping("/deleteuser/{account}")
    public boolean DeleteUser(@PathVariable String account) { return this.userService.DeleteUser(account);}
//    @GetMapping
//    public String list(){
//        return  "Hello";
//    }


}
