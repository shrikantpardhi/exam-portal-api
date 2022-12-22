package com.dynast.examportal.repository;

import com.dynast.examportal.model.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, String> {
}
