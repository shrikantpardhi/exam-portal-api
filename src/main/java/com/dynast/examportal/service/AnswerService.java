package com.dynast.examportal.service;

import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.model.Answer;
import com.dynast.examportal.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;


    public void delete(String answerId) {
        answerRepository.deleteById(answerId);
    }

    public Answer update(Answer answer) {
        return answerRepository.findById(answer.getAnswerId()).map(ans -> {
            ans.setAnswerText(answer.getAnswerText());
            ans.setQuestionId(answer.getQuestionId());
            ans.setAnswerImage(answer.getAnswerImage());
            ans.setIsCorrect(answer.getIsCorrect());
            return answerRepository.save(ans);
        }).orElseGet(() -> {
            return answerRepository.save(answer);
        });
    }

    public Answer create(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer getByQuestion(String questionId, String answerId) {
        return answerRepository.findByAnswerIdAndQuestionId(questionId, answerId).orElseThrow(
                () -> new NotFoundException("")
        );
    }

    public Iterable<Answer> getAllByQuestion(String questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    public Answer getByAnswerId(String answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("Answer not found with Id " + answerId));
    }
}
