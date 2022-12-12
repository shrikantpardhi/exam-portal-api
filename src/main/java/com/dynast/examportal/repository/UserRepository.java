package com.dynast.examportal.repository;

import com.dynast.examportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String userEmail);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmailOrMobile(String emailId, String mobile);

}
