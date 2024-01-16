package com.Task31533.Service;



import com.Task31533.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService  {
    void save(User user);

    List<User> getUserList();

    void delete(User user);

    User findById(Long id);
    User findByUsername(String name);

    List<User> findAll();
}
