package com.dynast.examportal.service;

import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.model.Role;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.RoleRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final com.dynast.examportal.util.User userUtil;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public UserService(UserRepository userRepository, com.dynast.examportal.util.User userUtil, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    public Iterable<UserDto> getAllUser() {
        Iterable<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(
                user -> userDtos.add(mapper.convertValue(user, UserDto.class))
        );
        return userDtos;
    }

    public UserDto registerNewUser(UserDto user) {
        return getCreateUserDto(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserDto updateUser(UserDto user) {
        return userRepository.findById(userUtil.getUsername()).map(u -> {
            u.setUserFirstName(user.getUserFirstName());
            u.setUserLastName(user.getUserLastName());
            u.setEmail(user.getEmail());
            u.setUserMobile(user.getUserMobile());
            u.setUserEducation(user.getUserEducation());
            u.setUserAddress(user.getUserAddress());
            u.setUserCity(user.getUserCity());
            u.setUserImage(user.getUserImage());
            return mapper.convertValue(userRepository.save(u), UserDto.class);
        }).orElseGet(() -> getCreateUserDto(user));
    }

    private UserDto getCreateUserDto(UserDto user) {
        Role role = roleRepository.findById("User").orElse(null);
        User u = mapper.convertValue(user, User.class);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        u.setRole(userRoles);
        u.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return mapper.convertValue(userRepository.save(u), UserDto.class);
    }

    public UserDto fetchUser(String emailId) {
        User user = userRepository.findByEmail(emailId).orElseThrow(() -> new NotFoundException(emailId));
        return mapper.convertValue(user, UserDto.class);
    }

    public UserDto getUserDetail(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException(userName));
        return mapper.convertValue(user, UserDto.class);
    }
}
