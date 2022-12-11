package com.dynast.examportal.controller;

import com.dynast.examportal.model.Tag;
import com.dynast.examportal.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "Tag related APIs", tags = {"Tag Controller"})
@RequestMapping(value = "/api/v1/tag/")
public class TagController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);
    @Autowired
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = {"all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    Iterable<Tag> all() {
        LOGGER.info("inside all");
        return tagService.all();
    }

    @GetMapping(value = {"search/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Tag> search(@ApiParam(name = "name", required = true) @PathVariable String name) {
        LOGGER.info("inside search {}", name);
        return tagService.search(name);
    }

    @PostMapping(value = {"create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Tag search(@ApiParam(name = "tag", required = true) @RequestBody Tag tag) {
        LOGGER.info("inside create {}", tag.getName());
        return tagService.create(tag);
    }
}
