package com.dynast.examportal.repository;

import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ExamRepository extends CrudRepository<Exam, String> {

    Optional<Exam> findByExamId(String examId);

    List<Exam> findAllByEducatorCode(EducatorCode educatorCode);

    List<Exam> findByUser(User user);

    List<Exam> findAllByEducatorCodeIn(Set<EducatorCode> educatorCodes);
}
