package com.dynast.examportal.repository;

import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Quiz;
import com.dynast.examportal.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends CrudRepository<Quiz, String> {
    Optional<List<Quiz>> findAllByUser(User user);

    List<Quiz> findAllByExamIn(List<Exam> exams);
}
