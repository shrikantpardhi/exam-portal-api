package com.dynast.examportal.repository;

import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface ExamRepository extends CrudRepository<Exam, String> {

    long countByExamCategory(@NotNull ExamCategory examCategory);

    long countBySubject(@NotNull Subject subject);

}
