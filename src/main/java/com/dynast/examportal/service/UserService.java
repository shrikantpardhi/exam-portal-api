package com.dynast.examportal.service;

import com.dynast.examportal.exception.UserNotFoundException;
import com.dynast.examportal.model.Role;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.RoleRepository;
import com.dynast.examportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.dynast.examportal.util.User userUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);
    }

    public Iterable<User> getAllUser() {

        return userRepository.findAll();
    }

    public User registerNewUser(User user) {
        Role role = roleRepository.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userRepository.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Optional<User> updateUser(User user) {
        return Optional.ofNullable(userRepository.findById(userUtil.getUsername()).map(u -> {
            u.setUserFirstName(user.getUserFirstName());
            u.setUserLastName(user.getUserLastName());
            u.setEmail(user.getEmail());
            u.setUserMobile(user.getUserMobile());
            u.setUserEducation(user.getUserEducation());
            u.setUserAddress(user.getUserAddress());
            u.setUserCity(user.getUserCity());
            u.setUserImage(user.getUserImage());
            return userRepository.save(u);
        }).orElseGet(() -> {
            user.setUserName(userUtil.getUsername());
            return userRepository.save(user);
        }));//.orElseThrow(() -> new UserCreationFailedException(user.toString()));
    }

    public User fetchUser(String emailId) {
        return userRepository.findByEmail(emailId).orElseThrow(() -> new UserNotFoundException(emailId));
    }

    public User getUserDetail(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }
}
