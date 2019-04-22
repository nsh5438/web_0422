package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.User;

import java.util.List;

public interface UserService {
    User AddUser(User user);

    List<User> ListUser();

    User UpdateUser(String account, User user);

    boolean DeleteUser(String account);
}
