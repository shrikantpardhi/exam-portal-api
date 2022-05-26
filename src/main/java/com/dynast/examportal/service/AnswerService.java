package com.dynast.examportal.service;

import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.model.Answer;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.repository.AnswerRepository;
import com.dynast.examportal.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public void delete(String answerId) {
        answerRepository.deleteById(answerId);
    }

    public Answer update(Answer answer) {
        Question question = questionRepository.findById(answer.getQuestion().getQuestionId()).orElseThrow(
                () -> new NotFoundException("Could Not find Question")
        );
        return answerRepository.findById(answer.getAnswerId()).map(ans -> {
            ans.setAnswerText(answer.getAnswerText());
            ans.setQuestion(question);
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
        Optional<Question> question = questionRepository.findById(questionId);
        return answerRepository.findByAnswerIdAndQuestion(answerId, question.get().getQuestionId()).orElseThrow(
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
