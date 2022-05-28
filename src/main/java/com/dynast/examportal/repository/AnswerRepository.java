package com.dynast.examportal.repository;

import com.dynast.examportal.model.Answer;
import com.dynast.examportal.model.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, String> {
    //    @Query("select u from User u where u.email = :email AND u.age >= :age")
    Optional<Answer> findByAnswerIdAndQuestion(String questionId, String answerId);

    @Query(value = "select a from answer where a.question_id = :questionId", nativeQuery = true)
    Iterable<Answer> findAllByQuestionId(@Param("questionId") String questionId);

    Iterable<Answer> findByQuestion(Question question);
}
