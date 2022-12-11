package com.dynast.examportal.service;

import com.dynast.examportal.model.Tag;
import lombok.NonNull;

import java.util.List;

public interface TagService {
    Tag create(@NonNull Tag tag);

    void delete(@NonNull Tag tag);

    List<Tag> search(String name);

    Iterable<Tag> all();
}
