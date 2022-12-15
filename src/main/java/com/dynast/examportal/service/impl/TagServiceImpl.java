package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.TagDto;
import com.dynast.examportal.model.Tag;
import com.dynast.examportal.repository.TagRepository;
import com.dynast.examportal.service.TagService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class TagServiceImpl implements TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);
    private final TagRepository tagRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto create(@NonNull TagDto tagDto) {
        LOGGER.info("inside create: {}", tagDto.getName());
        tagDto.setName(tagDto.getName().toUpperCase());
        Tag tag = tagRepository.save(mapper.convertValue(tagDto, Tag.class));
        return mapper.convertValue(tag, TagDto.class);
    }

    @Override
    public void delete(@NonNull TagDto tagDto) {
        LOGGER.info("inside delete: {}", tagDto.getName());
        tagRepository.deleteById(tagDto.getTagId());
    }

    @Override
    public Set<TagDto> search(String name) {
        LOGGER.info("inside search tag: {}", name);
        Set<Tag> tags =  tagRepository.findByNameContainingIgnoreCase(name);
        Set<TagDto> tagDtos = new HashSet<>();
        tags.forEach( tag -> tagDtos.add(mapper.convertValue(tag, TagDto.class)));
        return tagDtos;
    }

    @Override
    public Iterable<TagDto> all() {
        LOGGER.info("inside all");
        Iterable<Tag> tags = tagRepository.findAll();
        Set<TagDto> tagDtos = new HashSet<>();
        tags.forEach( tag -> tagDtos.add(mapper.convertValue(tag, TagDto.class)));
        return tagDtos;
    }
}
