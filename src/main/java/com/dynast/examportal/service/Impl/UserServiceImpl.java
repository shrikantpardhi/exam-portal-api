package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.model.Role;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.RoleRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.service.UserService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.dynast.examportal.util.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UserServiceImpl extends RoleServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initRoleAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName(Roles.ADMIN.getLabel());
        adminRole.setRoleDescription(Roles.ADMIN.getDescription());
//        roleRepository.save(adminRole);
        Set<Role> role = new HashSet<>();
        role.add(adminRole);
        User user = new User();
        user.setFirstName("Shri");
        user.setLastName("Pardhi");
        user.setEmail("admin@gmail.com");
        user.setMobile("8975307295");
        user.setPassword(getEncodedPassword("password"));
        user.setRole(role);
        userRepository.save(user);
    }


    @Override
    public Iterable<UserDto> getUsers() {
        LOGGER.info("inside getUsers.");
        Iterable<User> users = userRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
        return mapUserListToUserDtoList(users);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        LOGGER.info("inside createUser: " + userDto.getEmail());
        User user = mapper.convertValue(userDto, User.class);
        user.setRole(getUserRole());
        user.setPassword(getEncodedPassword(userDto.getPassword()));
        if (validateIfExist(userDto.getEmail(), userDto.getMobile())) {
            LOGGER.info("User already exist: " + userDto.getEmail());
        } else {
            user = userRepository.save(user);
        }
        return mapper.convertValue(user, UserDto.class);
    }

    @Override
    public UserDto createEducator(UserDto userDto) {
        LOGGER.info("inside createEducator: " + userDto.getEmail());
        User user = mapper.convertValue(userDto, User.class);
        user.setRole(getEducatorRole());
        user.setPassword(getEncodedPassword(userDto.getPassword()));
        if (validateIfExist(userDto.getEmail(), userDto.getMobile())) {
            LOGGER.info("User already exist: " + userDto.getEmail());
        } else {
            user = userRepository.save(user);
        }
        return mapper.convertValue(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        LOGGER.info("inside updateUser" + userDto.getEmail());
        return userRepository.findById(userDto.getUserId()).map(u -> {
            u.setFirstName(userDto.getFirstName());
            u.setLastName(userDto.getLastName());
            u.setEmail(userDto.getEmail());
            u.setMobile(userDto.getMobile());
            u.setEducation(userDto.getEducation());
            u.setAddress(userDto.getAddress());
            u.setCity(userDto.getCity());
            u.setState(userDto.getState());
            u.setStatus(userDto.getStatus());
            return mapper.convertValue(userRepository.save(u), UserDto.class);
        }).orElseGet(() -> {
            LOGGER.info("No user found. Creating a user." + userDto.getEmail());
            return createUser(userDto);
        });
    }

    @Override
    public Boolean updatePassword(UserDto userDto) {
        LOGGER.info("inside updatePassword: " + userDto.getEmail());
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        if (user.getPassword().equals(getEncodedPassword(userDto.getPassword()))) {
            user.setPassword(getEncodedPassword(userDto.getPassword()));
            userRepository.save(user);
            return true;
        } else {
            LOGGER.info("Old password doesn't match.");
            return false;
        }
    }

    @Override
    public Boolean resetPassword(String emailId) {
        LOGGER.info("inside resetPassword: " + emailId);
        User user = userRepository.findByEmail(emailId).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        /*
        * send an email to reset password*/
        return true;
    }

    @Override
    public UserDto getUserByEmailId(String emailId) {
        LOGGER.info("inside getUserByEmailId: " + emailId);
        User user = userRepository.findByEmail(emailId).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return mapper.convertValue(user, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        LOGGER.info("inside getUserById: " + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return mapper.convertValue(user, UserDto.class);
    }

    @Override
    public Boolean changeStatus(String userId) {
        LOGGER.info("inside disableUser: " + userId);
        User user = userRepository.findById(userId).map(user1 -> {
            user1.setStatus(!user1.getStatus());
            return userRepository.save(user1);
        }).orElseThrow(() -> new UsernameNotFoundException("No user found."));
        return !user.getStatus();
    }

    @Override
    public Boolean validateIfExist(String emailId, String mobile) {
        return userRepository.findByEmailOrMobile(emailId, mobile).isPresent();
    }

    private List<UserDto> mapUserListToUserDtoList(Iterable<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(mapper.convertValue(user, UserDto.class)));
        return userDtos;
    }


    private String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
