package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.EducatorCodeDto;
import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.dto.TagDto;
import com.dynast.examportal.exception.EducatorCodeNotFoundException;
import com.dynast.examportal.exception.ExamNotFoundException;
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
import com.dynast.examportal.repository.UserRepository;
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

    private final UserRepository userRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository, TagRepository tagRepository, EducatorRepository educatorRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
        this.educatorRepository = educatorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ExamDto create(ExamDto examDto) {
        LOGGER.info("inside create {}", examDto.getExamTitle());
        Set<TagDto> tagDtos = examDto.getTags();
        Set<Tag> tags = tagDtos.stream().map(tagDto -> mapper.convertValue(tagDto, Tag.class)).collect(Collectors.toSet());
        List<String> tagNames = examDto.getTags().stream().map(tag->tag.getName().toUpperCase()).collect(Collectors.toList());
        Set<Tag> finalTags = getFinalTags(tags, tagNames);
        User user = mapper.convertValue(examDto.getUser(), User.class);
        EducatorCode educatorCode = educatorRepository.findByCode(examDto.getEducatorCode().getCode())
                .orElseThrow(() -> {
                    LOGGER.info("Educator code not found. {}", examDto.getEducatorCode().getCode());
                    throw new EducatorCodeNotFoundException("No Educator Code found. " + examDto.getEducatorCode().getCode());
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
        Set<TagDto> tagDtos = examDto.getTags();
        Set<Tag> tags = tagDtos.stream().map(tagDto -> mapper.convertValue(tagDto, Tag.class)).collect(Collectors.toSet());
        List<String> tagNames = examDto.getTags().stream().map(tag->tag.getName().toUpperCase()).collect(Collectors.toList());
        Set<Tag> finalTags = getFinalTags(tags, tagNames);
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
        List<ExamDto> examDtos = new ArrayList<>();
        Iterable<Exam> exams = examRepository.findAll();
        exams.forEach(exam -> {
                    ExamDto examDto = mapper.convertValue(exam, ExamDto.class);
                    examDtos.add(examDto);
                }
        );
        return examDtos;
    }

    @Override
    public List<ExamDto> getByEducatorCode(String code) {
        LOGGER.info("inside getByEducatorCode {}", code);
        List<ExamDto> examDtos = null;
        EducatorCode educatorCode = educatorRepository.findByCode(code)
                .orElseThrow(() -> {
                    LOGGER.error("No Educator Code found for code {}", code);
                    throw new EducatorCodeNotFoundException("Educator Code not found");
                });
        List<Exam> exams = examRepository.findAllByEducatorCode(educatorCode);
        if (exams.isEmpty()) {
            LOGGER.info("No exams found for educator code : {}", code);
            return new ArrayList<>();
        }
        examDtos = exams.stream().map(exam -> mapper.convertValue(exam, ExamDto.class)).collect(Collectors.toList());
        LOGGER.info("Exams found {}", exams.size());
        return examDtos;
    }

    @Override
    public List<ExamDto> getByUser(String userId) {
        LOGGER.info("inside getByUser {}", userId);
        List<ExamDto> examDtos = null;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("Error. No user found {}", userId);
                    throw new NotFoundException("User not found.");
                });
        List<Exam> exams = examRepository.findByUser(user);
        examDtos = exams.stream().map(exam -> mapper.convertValue(exam, ExamDto.class)).collect(Collectors.toList());
        LOGGER.info("return exams {}", examDtos.size());
        return examDtos;
    }


    @Override
    public ExamDto getByExamId(String examId) {
        LOGGER.info("inside findByExamId {}", examId);
        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> {
                    LOGGER.info("no Exam found. {}", examId);
                    throw new ExamNotFoundException("Exam not found.");
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

    @Override
    public List<ExamDto> getByEducatorCodes(List<EducatorCodeDto> educatorCodeDtos) {
        LOGGER.info("In getByEducatorCodes. {}", educatorCodeDtos.size());
        List<ExamDto> examDtos = null;
        Set<EducatorCode> educatorCodes = educatorCodeDtos.stream()
                .map(educatorCodeDto -> mapper.convertValue(educatorCodeDto, EducatorCode.class))
                .collect(Collectors.toSet());
        List<Exam> exams = examRepository.findAllByEducatorCodeIn(educatorCodes);
        examDtos = exams.stream()
                .map(exam -> mapper.convertValue(exam, ExamDto.class))
                .collect(Collectors.toList());
        return examDtos;
    }

    @Override
    public List<ExamDto> getByUserEducatorCodes(String userId) {
        LOGGER.info("In getByUserEducatorCodes. userId = {}", userId);
        List<ExamDto> examDtos = null;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("Error. No user found {}", userId);
                    throw new NotFoundException("User not found.");
                });
        Set<EducatorCode> educatorCodes = user.getEducatorCodes().stream()
                .map(educatorCode -> mapper.convertValue(educatorCode, EducatorCode.class))
                .collect(Collectors.toSet());
        List<Exam> exams = examRepository.findAllByEducatorCodeIn(educatorCodes);
        examDtos = exams.stream().map(exam -> mapper.convertValue(exam, ExamDto.class)).collect(Collectors.toList());
        LOGGER.info("return exams {}", examDtos.size());
        return examDtos;
    }

    @Override
    public List<ExamDto> getByTag(String name) {
        LOGGER.info("Get exams by tag {}", name);
        Tag tag = tagRepository.findByName(name.toUpperCase()).orElseThrow(() -> new NotFoundException("Tag not found"));
        List<Exam> exams = examRepository.findAllByTags(tag);
        List<ExamDto> examDtos = exams.stream()
                .map(exam -> mapper.convertValue(exam, ExamDto.class))
                .collect(Collectors.toList());
        LOGGER.info("tag {}, exams {}", name, examDtos.size());
        return examDtos;
    }

    @Override
    public List<ExamDto> getByTags(List<String> names) {
        LOGGER.info("Get exams by tag {}", names);
        names = names.stream().map(String::toUpperCase).collect(Collectors.toList());
        Set<Tag> tags = tagRepository.findByNameIn(names);
        List<Exam> exams = examRepository.findAllByTagsIn(tags);
        List<ExamDto> examDtos = exams.stream()
                .map(exam -> mapper.convertValue(exam, ExamDto.class))
                .collect(Collectors.toList());
        LOGGER.info("exams {}", examDtos.size());
        return examDtos;
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
