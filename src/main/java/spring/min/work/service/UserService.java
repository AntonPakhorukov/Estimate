package spring.min.work.service;

import spring.min.work.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUserByName(String name);

}
