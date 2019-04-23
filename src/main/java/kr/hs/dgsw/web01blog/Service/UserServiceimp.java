package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.User;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web01blog.Protocol.ResponseType;
import kr.hs.dgsw.web01blog.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimp implements UserService {

    @Autowired
    private UserRep userRep;

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

}
