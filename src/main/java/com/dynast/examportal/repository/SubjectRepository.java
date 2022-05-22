package com.dynast.examportal.repository;

import com.dynast.examportal.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, String> {

}
