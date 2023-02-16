package com.zmo.bookmark.repository;

import com.zmo.bookmark.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findTagByTagNameIgnoreCase(String tagName);
    boolean existsTagByTagNameIgnoreCase(String tagName);

    Tag findTagById(Integer id);
}

