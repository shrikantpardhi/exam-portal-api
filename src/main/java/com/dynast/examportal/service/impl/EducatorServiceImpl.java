package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.EducatorCodeDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.EducatorRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.service.EducatorService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EducatorServiceImpl implements EducatorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EducatorServiceImpl.class);

    private final EducatorRepository educatorRepository;
    private final UserRepository userRepository;
    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public EducatorServiceImpl(EducatorRepository educatorRepository, UserRepository userRepository) {
        this.educatorRepository = educatorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EducatorCodeDto create(EducatorCodeDto educatorCodeDto, User user) {
        LOGGER.info("inside create: " + educatorCodeDto.getCode());
        Set<EducatorCode> educatorCodes = new HashSet<>();
        EducatorCode educatorCode = mapper.convertValue(educatorCodeDto, EducatorCode.class);
        educatorCode = educatorRepository.save(educatorCode);
        educatorCodes.add(educatorCode);
        educatorCodes.addAll(user.getEducatorCodes());
        user.setEducatorCodes(educatorCodes);
        userRepository.saveAndFlush(user);
        return mapper.convertValue(educatorCode, EducatorCodeDto.class);
    }

    @Override
    public EducatorCodeDto update(EducatorCodeDto educatorCodeDto, User userDto) {
        LOGGER.info("inside update: " + educatorCodeDto.getCode());
        educatorRepository.findById(educatorCodeDto.getCodeId()).map(eCode -> {
            eCode.setCode(educatorCodeDto.getCode());
            eCode.setDescription(educatorCodeDto.getDescription());
            eCode.setIsProtected(educatorCodeDto.getIsProtected());
            return educatorRepository.save(eCode);
        }).orElseThrow(() -> {
            LOGGER.info("error in update : " + educatorCodeDto.getCode());
            throw new UnprocessableEntityException("Unable to update code.");
        });
        return educatorCodeDto;
    }

    @Override
    public void delete(EducatorCodeDto educatorCode) {
        LOGGER.info("inside delete: " + educatorCode.getCode());
        educatorRepository.deleteById(educatorCode.getCodeId());
    }

    @Override
    public List<EducatorCodeDto> fetchAll() {
        LOGGER.info("inside fetchAll.");
        List<EducatorCodeDto>  educatorCodeDtos = new ArrayList<>();
        educatorRepository.findAll().forEach(
                educatorCode -> educatorCodeDtos.add(mapper.convertValue(educatorCode, EducatorCodeDto.class))
        );
        return educatorCodeDtos;
    }

    @Override
    public EducatorCodeDto fetchByCode(String code) {
        LOGGER.info("inside fetchByCode : {}", code);
        EducatorCode educatorCode = educatorRepository.findByCode(code).orElseThrow(() -> {
            LOGGER.error("No Code found : {}", code);
            throw new NotFoundException("Educator code not found");
        });
        return mapper.convertValue(educatorCode, EducatorCodeDto.class);
    }
}