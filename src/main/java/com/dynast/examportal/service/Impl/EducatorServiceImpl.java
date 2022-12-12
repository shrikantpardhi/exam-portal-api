package com.dynast.examportal.service.Impl;

import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.EducatorRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.service.EducatorService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class EducatorServiceImpl implements EducatorService {
    private static final Logger LOGGER = Logger.getLogger(EducatorServiceImpl.class.getName());

    private final EducatorRepository educatorRepository;
    private final UserRepository userRepository;

    public EducatorServiceImpl(EducatorRepository educatorRepository, UserRepository userRepository) {
        this.educatorRepository = educatorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EducatorCode create(EducatorCode educatorCode, User user) {
        LOGGER.info("inside create: " + educatorCode.getCode());
        Set<EducatorCode> educatorCodes = new HashSet<>();
        EducatorCode eduCode = educatorRepository.save(educatorCode);
        educatorCodes.add(eduCode);
        educatorCodes.addAll(userRepository.findByEmail(user.getEmail()).get().getEducatorCode());
        user.setEducatorCode(educatorCodes);
        userRepository.saveAndFlush(user);
        return eduCode;
    }

    @Override
    public EducatorCode update(EducatorCode educatorCode, User userDto) {
        LOGGER.info("inside update: " + educatorCode.getCode());
        return educatorRepository.findById(educatorCode.getCodeId()).map(eCode -> {
            eCode.setCode(educatorCode.getCode());
            eCode.setDescription(educatorCode.getDescription());
            return educatorRepository.save(eCode);
        }).orElseThrow(() -> {
            LOGGER.info("error in update : " + educatorCode.getCode());
            throw new UnprocessableEntityException("Unable to update code.");
        });
    }

    @Override
    public void delete(EducatorCode educatorCode) {
        LOGGER.info("inside delete: " + educatorCode.getCode());
        educatorRepository.delete(educatorCode);
    }

    @Override
    public List<EducatorCode> fetchAll() {
        LOGGER.info("inside fetchAll.");
        return educatorRepository.findAll();
    }

    @Override
    public EducatorCode fetchByCode(String code) {
        LOGGER.info("inside fetchAll.");
        return educatorRepository.findByCode(code).orElseThrow(() -> {
            LOGGER.info("No Code found.");
            throw new NotFoundException("Educator code not found");
        });
    }
}