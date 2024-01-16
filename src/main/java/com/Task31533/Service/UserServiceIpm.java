package com.Task31533.Service;



import com.Task31533.model.User;
import com.Task31533.repository.UserRepository;
import com.Task31533.util.PersonNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceIpm implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceIpm(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }



    @Override
    @Transactional
    public User findById(Long id) {
       return userRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }


    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     *
     * Использую здесь два уже существующих метода, что бы сначала проверить есть ли пользователь с таким именем,
     * а потом удалить его. А если такого нет, то получить уже существующие исключение в методе findById.
     */
    @Transactional
    public void deleteById(Long id){
        User user = findById(id);
        delete(user);
    }

    public void update(User user) {
        passwordEncoder.encode(user.getPassword());
        userRepository.save(user);
    }
}
