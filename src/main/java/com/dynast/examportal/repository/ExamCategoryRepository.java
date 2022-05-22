package com.dynast.examportal.repository;

import com.dynast.examportal.model.ExamCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamCategoryRepository extends CrudRepository<ExamCategory, String> {
}
