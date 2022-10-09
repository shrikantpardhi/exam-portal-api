package com.dynast.examportal.service.Impl;

import com.dynast.examportal.service.UserService;
import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Role;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.RoleRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final com.dynast.examportal.util.User userUtil;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public UserServiceImpl(UserRepository userRepository, com.dynast.examportal.util.User userUtil, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
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
        userRole.setRoleDescription("User role");
        roleRepository.save(userRole);

//        User adminUser = new User();
//        adminUser.setEmail("admin@gmail.com");
//        adminUser.setPassword(getEncodedPassword("admin@pass"));
//        adminUser.setFirstName("admin");
//        adminUser.setLastName("admin");
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        adminUser.setRole(adminRoles);
//        userRepository.save(adminUser);

    }

    @Override
    public Iterable<UserDto> getAllUser() {
        logger.info(() -> "Get list of users");
        Iterable<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(
                user -> userDtos.add(mapper.convertValue(user, UserDto.class))
        );
        return userDtos;
    }

    @Override
    public UserDto registerNewUser(UserDto user) {
        return getCreateUserDto(user);
    }

    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public UserDto updateUser(UserDto user) {
        logger.info(() -> "Update user {}"+user.getEmail());
        return userRepository.findById(userUtil.getUsername()).map(u -> {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setMobile(user.getMobile());
            u.setEducation(user.getEducation());
            u.setAddress(user.getAddress());
            u.setCity(user.getCity());
            u.setImage(user.getImage());
            return mapper.convertValue(userRepository.save(u), UserDto.class);
        }).orElseGet(() -> getCreateUserDto(user));
    }

    private UserDto getCreateUserDto(UserDto user) {
        Boolean status = validateIfExist(user.getEmail(), user.getMobile());
        if (status)
            throw new UnprocessableEntityException("Email or Mobile is already present");
        Role role = roleRepository.findById("User").orElse(null);
        User user1 = mapper.convertValue(user, User.class);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user1.setRole(userRoles);
        user1.setPassword(getEncodedPassword(user.getPassword()));
        return mapper.convertValue(userRepository.save(user1), UserDto.class);
    }

    @Override
    public UserDto fetchUser(String emailId) {
        User user = userRepository.findByEmail(emailId).orElseThrow(() -> new NotFoundException(emailId));
        return mapper.convertValue(user, UserDto.class);
    }

    @Override
    public UserDto getUserDetail(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException(userName));
        return mapper.convertValue(user, UserDto.class);
    }

    @Override
    public Boolean validateIfExist(String emailId, String mobile) {
        return userRepository.findByEmailOrMobile(emailId, mobile).isPresent();
    }
}
