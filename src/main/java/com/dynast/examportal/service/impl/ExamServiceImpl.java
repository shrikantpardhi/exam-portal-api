package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Tag;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.EducatorRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.repository.TagRepository;
import com.dynast.examportal.service.ExamService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExamServiceImpl implements ExamService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamServiceImpl.class);
    private final ExamRepository examRepository;

    private final QuestionRepository questionRepository;

    private final TagRepository tagRepository;

    private final EducatorRepository educatorRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository, TagRepository tagRepository, EducatorRepository educatorRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
        this.educatorRepository = educatorRepository;
    }

    @Override
    public ExamDto create(ExamDto examDto) {
        LOGGER.info("inside create {}", examDto.getExamTitle());
        Set<Tag> examTags = examDto.getTags();
        List<String> tagNames = examDto.getTags().stream().map(tag->tag.getName()).collect(Collectors.toList());
        Set<Tag> finalTags = getFinalTags(examTags, tagNames);
        User user = mapper.convertValue(examDto.getUserDto(), User.class);
        EducatorCode educatorCode = educatorRepository.findByCode(examDto.getEducatorCode().getCode())
                .orElseThrow(() -> {
                    LOGGER.info("Educator code not found. {}", examDto.getEducatorCode().getCode());
                    throw new NotFoundException("No Educator Code found. " + examDto.getEducatorCode().getCode());
                });
        Exam exam = mapper.convertValue(examDto, Exam.class);
        exam.setEducatorCode(educatorCode);
        exam.setUser(user);
        exam.setTags(finalTags);
        exam = examRepository.save(exam);
        ExamDto dto = mapper.convertValue(exam, ExamDto.class);
        return dto;
    }

    @Override
    public ExamDto update(ExamDto examDto) {
        LOGGER.info("inside update {}", examDto.getExamId());
        Set<Tag> examTags = examDto.getTags();
        List<String> tagNames = examDto.getTags().stream().map(tag->tag.getName()).collect(Collectors.toList());
        Set<Tag> finalTags = getFinalTags(examTags, tagNames);
        Exam exam = examRepository.findById(examDto.getExamId()).map(e -> {
            e = mapper.convertValue(examDto, Exam.class);
            e.setTags(finalTags);
            return examRepository.save(e);
        }).orElseThrow(() -> {
            LOGGER.info("Unable to update an Exam. {}", examDto.getExamId());
            throw new UnprocessableEntityException("Unable to update an Exam");
        });
        ExamDto dto = mapper.convertValue(exam, ExamDto.class);
        return dto;
    }

    @Override
    public void delete(String examId) {
        LOGGER.info("inside delete {}", examId);
        examRepository.deleteById(examId);
    }

    @Override
    public List<ExamDto> getAll() {
        LOGGER.info("inside getAll");
        Iterable<Exam> exams = examRepository.findAll();
        List<ExamDto> examDtos = new ArrayList<>();
        exams.forEach(exam -> {
                    ExamDto examDto = mapper.convertValue(exam, ExamDto.class);
                    examDtos.add(examDto);
                }
        );
        return examDtos;
    }

    @Override
    public ExamDto findByExamId(String examId) {
        LOGGER.info("inside findByExamId {}", examId);
        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> {
                    LOGGER.info("no Exam found. {}", examId);
                    throw new NotFoundException("No Exam Found.");
                });
        return mapper.convertValue(exam, ExamDto.class);
    }

    @Override
    public ExamDto changeStatus(String examId) {
        LOGGER.info("In changeStatus. {}", examId);
        Exam exam = examRepository.findById(examId).map(exam1 -> {
            exam1.setStatus(!exam1.getStatus());
            return examRepository.save(exam1);
        }).orElseThrow(() -> {
            LOGGER.info("Unable to change status an Exam. {}", examId);
            throw new UnprocessableEntityException("Unable to change status an Exam.");
        });
        return mapper.convertValue(exam, ExamDto.class);
    }

    private Set<Tag> getFinalTags(Set<Tag> tagSet, List<String> tagNames) {
        LOGGER.info("In getFinalTags. If tags are not present then add to DB {}", tagNames);
        Set<Tag> tags = tagRepository.findByNameIn(tagNames);
        List<String> tagNamesPresent = tags.stream().map(tag->tag.getName()).collect(Collectors.toList());
        Set<Tag> tagToSave = tagSet.stream().filter(tag -> !tagNamesPresent.contains(tag.getName()))
                .collect(Collectors.toSet());
        Set<Tag> finalSet = tags;
        if(!tagToSave.isEmpty()) {
            Iterable<Tag> tagSaved = tagRepository.saveAll(tagToSave);
            finalSet = Stream.concat(tags.stream(), Streamable.of(tagSaved).toSet().stream())
                    .collect(Collectors.toSet());
        }
        return finalSet;
    }
}
