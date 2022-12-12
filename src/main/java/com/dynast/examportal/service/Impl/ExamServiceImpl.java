package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.exception.NotFoundException;
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
        EducatorCode educatorCode = educatorRepository.findByCode(examDto.getEducatorCode().getCode()).orElseThrow(() -> new NotFoundException("No Educator Code found. "+ examDto.getEducatorCode().getCode()));
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
        LOGGER.info("inside update {}", examDto.getExamTitle());
        Set<Tag> examTags = examDto.getTags();
        List<String> tagNames = examDto.getTags().stream().map(tag->tag.getName()).collect(Collectors.toList());
        Set<Tag> finalTags = getFinalTags(examTags, tagNames);
        return examRepository.findById(examDto.getExamId()).map(exam -> {
            exam = mapper.convertValue(examDto, Exam.class);
            exam.setTags(finalTags);
            exam.setUser(mapper.convertValue(examDto.getUserDto(), User.class));
            return mapper.convertValue(examRepository.save(exam), ExamDto.class);
        }).orElseThrow(() -> new NotFoundException("No Exam found!." + examDto.getExamTitle()));
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
                    examDto.setUserDto(mapper.convertValue(exam.getUser(), UserDto.class));
                    examDtos.add(examDto);
                }
        );
        return examDtos;
    }

    @Override
    public ExamDto findByExamId(String examId) {
        LOGGER.info("inside findByExamId {}", examId);
        Exam exam = examRepository.findByExamId(examId).get();//.orElseThrow(() -> new NotFoundException("No exam found!. " + examId));
        ExamDto examDto = mapper.convertValue(exam, ExamDto.class);
        examDto.setUserDto(mapper.convertValue(exam.getUser(), UserDto.class));
        return examDto;
    }

    @Override
    public ExamDto changeStatus(ExamDto examDto) {
        return null;
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
