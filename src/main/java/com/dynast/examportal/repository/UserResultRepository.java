package com.dynast.examportal.repository;

import com.dynast.examportal.model.User;
import com.dynast.examportal.model.UserResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserResultRepository extends CrudRepository<UserResult, String> {
    Iterable<UserResult> findByUser(User user);

    Optional<UserResult> findByUserAndResultId(User user, String resultId);
}
