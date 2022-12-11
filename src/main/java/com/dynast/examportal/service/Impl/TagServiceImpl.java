package com.dynast.examportal.service.Impl;

import com.dynast.examportal.model.Tag;
import com.dynast.examportal.repository.TagRepository;
import com.dynast.examportal.service.TagService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagServiceImpl implements TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag create(@NonNull Tag tag) {
        LOGGER.info("inside create: {}", tag.getName());
        return tagRepository.save(tag);
    }

    @Override
    public void delete(@NonNull Tag tag) {
        LOGGER.info("inside delete: {}", tag.getName());
        tagRepository.delete(tag);
    }

    @Override
    public List<Tag> search(String name) {
        LOGGER.info("inside search tag: {}", name);
        return tagRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Iterable<Tag> all() {
        LOGGER.info("inside all");
        return tagRepository.findAll();
    }
}
