package com.dynast.examportal.repository;

import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {

    Optional<Exam> findByExamId(String examId);

    List<Exam> findAllByEducatorCode(EducatorCode educatorCode);

    List<Exam> findByUser(User user);
}
