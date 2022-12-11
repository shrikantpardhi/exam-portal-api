package com.dynast.examportal.repository;

import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {
    Iterable<Question> findByExam(@NotNull Exam exam);
    int countByExam(@NotNull Exam exam);
}
