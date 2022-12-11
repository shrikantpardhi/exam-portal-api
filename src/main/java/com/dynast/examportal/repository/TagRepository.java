package com.dynast.examportal.repository;

import com.dynast.examportal.model.Tag;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, String> {

    List<Tag> findByNameContainingIgnoreCase(@NonNull String name);

}
