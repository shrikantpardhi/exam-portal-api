package com.dynast.examportal.service;

import com.dynast.examportal.dto.AnswerDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Answer;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.repository.AnswerRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public void delete(String answerId) {
        answerRepository.deleteById(answerId);
    }

    public AnswerDto update(AnswerDto answer) {
        Question question = questionRepository.findById(answer.getQuestionDto().getQuestionId()).orElseThrow(
                () -> new NotFoundException("Could Not find Question")
        );
        return answerRepository.findById(answer.getAnswerId()).map(ans -> {
            ans.setAnswerText(answer.getAnswerText());
            ans.setQuestion(question);
            ans.setAnswerImage(answer.getAnswerImage());
            ans.setIsCorrect(answer.getIsCorrect());
            return mapper.convertValue(answerRepository.save(ans), AnswerDto.class);
        }).orElseThrow(
                () -> new UnprocessableEntityException("Unable to update answer")
        );
    }

    public AnswerDto create(AnswerDto answer) {
        Answer ans = mapper.convertValue(answer, Answer.class);
        return mapper.convertValue(ans, AnswerDto.class);
    }

//    public AnswerDto getByQuestion(String questionId, String answerId) {
//        Optional<Question> question = questionRepository.findById(questionId);
//        return answerRepository.findByAnswerIdAndQuestion(answerId, question.get().getQuestionId()).orElseThrow(
//                () -> new NotFoundException("")
//        );
//    }

    public Iterable<AnswerDto> getAllByQuestion(String questionId) {
        Iterable<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        List<AnswerDto> answerDtoList = new ArrayList<>();
        answers.forEach(
                answer -> answerDtoList.add(mapper.convertValue(answer, AnswerDto.class))
        );
        return answerDtoList;
    }

    public AnswerDto getByAnswerId(String answerId) {
        Answer answer =  answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("Answer not found with Id " + answerId));
        return mapper.convertValue(answer, AnswerDto.class);
    }
}
