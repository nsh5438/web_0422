package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Domain.User;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web01blog.Protocol.ResponseType;
import kr.hs.dgsw.web01blog.Repository.PostRep;
import kr.hs.dgsw.web01blog.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimp implements UserService {

    @Autowired
    private UserRep userRep;

    @Autowired
    private PostRep postRep;

    @PostConstruct
    private void init() {

        if (this.userRep.count() > 0) return;
        User user = this.userRep.save(new User("abc","1234","abc",
                "abc@dgsw.hs.kr","010-1111-1111",
                "D:/3102_남가영/IdeaProjects/dgsw_sns/upload/f166a6f5-5a96-47c7-b696-4d83f2302e88 flower.jpg"
                ));

        this.postRep.save(new Post(user.getAccount(), "title1","abc1111"));
        this.postRep.save(new Post(user.getAccount(),"title2","abc2222"));
        this.postRep.save(new Post(user.getAccount(),"title3","abc3333"));
    }

    @Override
    public ResponseFormat AddUser(User user) {
        Optional<User> found = this.userRep.findByAccount(user.getAccount());
        if (found.isPresent()) return null;
        this.userRep.save(user);
        return new ResponseFormat(ResponseType.USER_ADD, user, user.getAccount());
    }

    @Override
    public ResponseFormat ListUser() {
        return new ResponseFormat(ResponseType.USER_GET,this.userRep.findAll());
    }

    @Override
    public ResponseFormat UpdateUser(String account, User user) {
        User updateuser = this.userRep.findByAccount(account)
                .map(found -> {
                    found.setName(Optional.ofNullable(user.getName()).orElse(found.getName()));
                    found.setPassword(Optional.ofNullable(user.getPassword()).orElse(found.getPassword()));
                    found.setEmail(Optional.ofNullable(user.getEmail()).orElse(found.getEmail()));
                    found.setPhone(Optional.ofNullable(user.getPhone()).orElse(found.getPhone()));
                    return this.userRep.save(found);
                })
                .orElse(null);
        return new ResponseFormat(ResponseType.USER_UPDATE,updateuser,account);
    }

    @Override
    public ResponseFormat DeleteUser(String account) {
        Optional<User> found = this.userRep.findByAccount(account);
        if (found.isPresent()) {
            this.userRep.delete(found.get());
            return new ResponseFormat(ResponseType.USER_DELETE,found,account);
        } else
            return new ResponseFormat(ResponseType.FAIL,found);
    }

    @Override
    public ResponseFormat findUser(String account) {
        Optional<User> user = this.userRep.findByAccount(account);
        return new ResponseFormat(ResponseType.USER_GET, user);
    }

}
