package com.dynast.examportal.repository;

import com.dynast.examportal.model.QuestionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends CrudRepository<QuestionType, String> {
}
