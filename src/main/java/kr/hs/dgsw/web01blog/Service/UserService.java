package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.User;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;

import java.util.List;

public interface UserService {
    ResponseFormat AddUser(User user);

    ResponseFormat ListUser();

    ResponseFormat UpdateUser(String account, User user);

    ResponseFormat DeleteUser(String account);
}
