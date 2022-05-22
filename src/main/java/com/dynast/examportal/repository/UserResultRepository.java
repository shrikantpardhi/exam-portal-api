package com.dynast.examportal.repository;

import com.dynast.examportal.model.UserResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResultRepository extends CrudRepository<UserResult, String> {
}
