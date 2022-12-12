package com.dynast.examportal.repository;

import com.dynast.examportal.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {

    Optional<Exam> findByExamId(String examId);
}
