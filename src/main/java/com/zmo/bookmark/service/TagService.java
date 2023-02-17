package com.zmo.bookmark.service;

import com.zmo.bookmark.dto.TagDTO;
import com.zmo.bookmark.exception.EntityNotFoundException;
import com.zmo.bookmark.model.Bookmark;
import com.zmo.bookmark.model.Tag;
import com.zmo.bookmark.repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.print.Book;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TagService {

    private final TagRepository repository;

    public Tag findByName(final String tagName){
            return repository.findTagByTagNameIgnoreCase(tagName)
                    .orElseThrow(() -> new EntityNotFoundException(Tag.class, tagName));
    }

    public Set<Bookmark> findLinkedBookmarks(String tagName){
        return findByName(tagName).getTaggedBookmarks();
    }

    public List<Tag> findAll() {
        return repository.findAll();
    }

    public Tag create(Tag dto) {
        boolean isTagExist = repository.existsTagByTagNameIgnoreCase(dto.getTagName());

        if (!isTagExist){
            Tag tag = Tag.builder()
                    .tagName(dto.getTagName())
                    .createdDate(Instant.now())
                    .build();
            log.info("Tag " + dto.getTagName() + " is created.");
            return repository.save(tag);
        }else {
            throw new RuntimeException("Tag \"" + dto.getTagName() + "\" already exists");
        }

    }

    public Tag findById(Integer tagId) {
       return repository.findTagById(tagId);
    }
}
