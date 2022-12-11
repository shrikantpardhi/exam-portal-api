package com.dynast.examportal.repository;

import com.dynast.examportal.model.Exam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam, String> {

}
