package com.dynast.examportal.repository;

import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {
    List<Question> findByExam(@NotNull Exam exam);

    List<Question> findByTag(Tag tag);
}
