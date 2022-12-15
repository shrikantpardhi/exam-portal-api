package com.dynast.examportal.service;

import com.dynast.examportal.dto.TagDto;
import lombok.NonNull;

import java.util.Set;

public interface TagService {
    TagDto create(@NonNull TagDto tag);

    void delete(@NonNull TagDto tag);

    Set<TagDto> search(String name);

    Iterable<TagDto> all();
}
