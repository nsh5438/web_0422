package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.User;
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
    public User AddUser(User user) {
        Optional<User> found = this.userRep.findByAccount(user.getAccount());
        if (found.isPresent()) return null;
        return this.userRep.save(user);
    }

    @Override
    public List<User> ListUser() {
        return this.userRep.findAll();
    }

    @Override
    public User UpdateUser(String account, User user) {
        return this.userRep.findByAccount(account)
                .map(found -> {
                    found.setName(Optional.ofNullable(user.getName()).orElse(found.getName()));
                    found.setPassword(Optional.ofNullable(user.getPassword()).orElse(found.getPassword()));
                    found.setEmail(Optional.ofNullable(user.getEmail()).orElse(found.getEmail()));
                    found.setPhone(Optional.ofNullable(user.getPhone()).orElse(found.getPhone()));
                    return this.userRep.save(found);
                })
                .orElse(null);
    }

    @Override
    public boolean DeleteUser(String account) {
        Optional<User> found = this.userRep.findByAccount(account);
        if (found.isPresent()) {
            this.userRep.delete(found.get());
            return true;
        } else
            return false;
    }

}
