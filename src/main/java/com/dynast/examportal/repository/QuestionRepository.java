package com.dynast.examportal.repository;

import com.dynast.examportal.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {
}
