package com.dynast.examportal.repository;

import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {
    Iterable<Question> findByExam(Exam exam);

    Iterable<Question> findBySubject(Subject subject);
}
