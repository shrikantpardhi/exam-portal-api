package com.dynast.examportal.repository;

import com.dynast.examportal.model.User;
import com.dynast.examportal.model.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserResultRepository extends CrudRepository<Result, String> {
    Iterable<Result> findByUser(User user);

    Optional<Result> findByUserAndResultId(User user, String resultId);
}
