package com.dynast.examportal.service;

import com.dynast.examportal.model.Tag;
import lombok.NonNull;

import java.util.Set;

public interface TagService {
    Tag create(@NonNull Tag tag);

    void delete(@NonNull Tag tag);

    Set<Tag> search(String name);

    Iterable<Tag> all();
}
