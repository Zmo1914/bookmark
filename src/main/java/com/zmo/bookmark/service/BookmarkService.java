package com.zmo.bookmark.service;

import com.zmo.bookmark.dto.BookmarkDTO;
import com.zmo.bookmark.exception.EntityAlreadyExistsException;
import com.zmo.bookmark.exception.EntityNotFoundException;
import com.zmo.bookmark.model.Bookmark;
import com.zmo.bookmark.model.Tag;
import com.zmo.bookmark.repository.BookmarkRepository;
import com.zmo.bookmark.repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    private final TagRepository tagRepository;

    //@Cacheable
    public Bookmark getBookmarkById(final Integer id) {

        return bookmarkRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Bookmark.class, id));
    }

    public Bookmark getBookmarkByName(String name) {
        return bookmarkRepository.findBookmarkByNameIgnoreCase(name.toLowerCase())
                .orElseThrow(() -> new EntityNotFoundException(Bookmark.class, name));
    }

    @Transactional
    public BookmarkDTO create(final BookmarkDTO entity) {
        boolean isBookmarkExist = bookmarkRepository.existsBookmarkByNameIgnoreCase(entity.getName());

        if (!isBookmarkExist) {
            Bookmark bookmark = Bookmark.builder()
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .enabled(true)
                    .path(entity.getPath())
                    .createdDate(Instant.now())
                    .build();

            bookmarkRepository.save(bookmark);
            log.info("Bookmark " + entity.getName() + " is added.");
            return BookmarkDTO.of(bookmark);
        } else {
            throw new RuntimeException("Bookmark \"" + entity.getName() + "\" already exists");
        }
    }

    public Bookmark update(Integer id, BookmarkDTO dto) {
        if (!(id == dto.getBookmarkId())) {
            throw new RuntimeException("Bookmark id is not equal to dto bookmark id");
        }
        Bookmark entity = getBookmarkById(id);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPath(dto.getPath());

        return bookmarkRepository.save(entity);
    }

    public List<Bookmark> findAll() {
        return bookmarkRepository.findAll();
    }

    public Bookmark addTag(Integer bookmarkId, Integer tagId) {
        Bookmark bookmark = getBookmarkById(bookmarkId);
        Tag tag = tagRepository.findTagById(tagId);
        if (tag == null) {
            throw new EntityNotFoundException(Tag.class, tagId);
        }
        if (bookmark.getLinkedTags().stream().anyMatch(t -> t.getTagName()
                .toLowerCase().contains(tag.getTagName().toLowerCase()))) {
            throw new EntityAlreadyExistsException(Tag.class, tag.getTagName());
        }
        bookmark.addTag(tagRepository.findTagById(tagId));

        return bookmarkRepository.save(bookmark);
    }
}
