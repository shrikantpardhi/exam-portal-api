package com.dynast.examportal.repository;

import com.dynast.examportal.model.EducatorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducatorRepository extends JpaRepository<EducatorCode, String> {

    Optional<EducatorCode> findByCode(String code);

}
