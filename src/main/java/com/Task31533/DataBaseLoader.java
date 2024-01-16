package com.Task31533;

import com.Task31533.Service.RoleServiceImp;
import com.Task31533.Service.UserService;
import com.Task31533.model.Role;
import com.Task31533.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class DataBaseLoader implements CommandLineRunner {
    private final UserService userService;
    private final RoleServiceImp roleService;

    public DataBaseLoader(UserService userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        Role ROLE_USER = new Role(1L, "ROLE_USER");
        roleService.save(ROLE_USER);

        Role ROLE_ADMIN = new Role(2L, "ROLE_ADMIN");
        roleService.save(ROLE_ADMIN);

        User user = new User("user", "user", 13, "fafi@gmail.ru", "1234");
        user.setRoles(new HashSet<>(List.of(ROLE_USER)));
        userService.save(user);

        User admin = new User("Admin", "admin", 42, "joja@gmail.ru", "123456");
        admin.setRoles(new HashSet<>(List.of(ROLE_ADMIN, ROLE_USER)));
        userService.save(admin);
    }
}
