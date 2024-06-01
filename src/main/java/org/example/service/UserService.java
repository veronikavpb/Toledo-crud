package org.example.service;

import jakarta.transaction.Transactional;
import org.example.enity.Role;
import org.example.enity.User;
import org.example.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService  {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }

    public List<User> getAllStudents() {
        return userRepo.findAll().stream()
                .filter(user -> user.getRoles().contains(Role.ROLE_STUDENT))
                .collect(Collectors.toList());
    }

    public User getUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Wrong username: " + username));
    }

    public boolean addNewUser(String fullname, String username, String password, String role) {
        if (userRepo.findByUsername(username).isPresent()) {
            return false;
        }
        User user = new User();
        user.setFullname(fullname);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of(Role.valueOf(role)));
        userRepo.save(user);
        return true;
    }
}
