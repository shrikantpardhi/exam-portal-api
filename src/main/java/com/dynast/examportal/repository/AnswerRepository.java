package com.dynast.examportal.repository;

import com.dynast.examportal.model.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, String> {
}
