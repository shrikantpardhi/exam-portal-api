package com.dynast.examportal.service;

import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class ProfileImageService {

    @Autowired
    private com.dynast.examportal.util.User userUtil;

    @Autowired
    private UserRepository userRepository;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Optional<User> uploadImage(User user) {
        return userRepository.findById(userUtil.getUsername())
                .map(u -> {
                    u.setUserImage(user.getUserImage());
                    u.setUpdated(new Date());
                    return userRepository.save(u);
                });

    }
}
