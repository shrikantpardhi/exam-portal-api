package com.dynast.examportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends CrudRepository<QuestionType, String> {
}
