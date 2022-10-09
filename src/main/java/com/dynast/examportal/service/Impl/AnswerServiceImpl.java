package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.AnswerDto;
import com.dynast.examportal.dto.QuestionDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Answer;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.repository.AnswerRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.service.AnswerService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void delete(String answerId) {
        answerRepository.deleteById(answerId);
    }

    @Override
    public AnswerDto update(AnswerDto answer) {
        return answerRepository.findById(answer.getAnswerId()).map(ans -> {
            ans = createAnswer(answer);
            return toAnswerDto(answerRepository.save(ans));
        }).orElseThrow(
                () -> new UnprocessableEntityException("Unable to update answer")
        );
    }

    @Override
    public AnswerDto create(AnswerDto answer) {
        Answer ans = createAnswer(answer);
        return toAnswerDto(answerRepository.save(ans));
    }

    @Override
    public Iterable<AnswerDto> getAllByQuestion(String questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new NotFoundException("Question Not found")
        );
        Iterable<Answer> answers = answerRepository.findByQuestion(question);
        List<AnswerDto> answerDtoList = new ArrayList<>();
        answers.forEach(
                answer -> answerDtoList.add(toAnswerDto(answer))
        );
        return answerDtoList;
    }

    @Override
    public AnswerDto getByAnswerId(String answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("Answer not found"));
        return toAnswerDto(answer);
    }

    private AnswerDto toAnswerDto(Answer ans) {
        AnswerDto answerDto = mapper.convertValue(ans, AnswerDto.class);
        answerDto.setQuestionDto(mapper.convertValue(ans.getQuestion(), QuestionDto.class));
        return answerDto;
    }

    private Answer createAnswer(AnswerDto answerDto) {
        Question question = questionRepository.findById(answerDto.getQuestionDto().getQuestionId()).orElseThrow(
                () -> new NotFoundException("Could not find the question")
        );
        Answer answer = mapper.convertValue(answerDto, Answer.class);
        answer.setQuestion(question);
        return answer;
    }
}
