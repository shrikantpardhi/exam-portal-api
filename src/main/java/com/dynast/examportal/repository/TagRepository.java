package com.dynast.examportal.repository;

import com.dynast.examportal.model.Tag;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends CrudRepository<Tag, String> {

    Set<Tag> findByNameContainingIgnoreCase(@NonNull String name);

    Optional<Tag> findByNameIgnoreCase(String name);

    Set<Tag> findByNameIn(List<String>names);

//    Set<Tag> saveAll(Set<Tag> tags);

}
