package com.zmo.bookmark.controller;

import com.zmo.bookmark.dto.ApiListResponse;
import com.zmo.bookmark.dto.BookmarkDTO;
import com.zmo.bookmark.dto.TagDTO;
import com.zmo.bookmark.exception.EntityAlreadyExistsException;
import com.zmo.bookmark.exception.EntityNotFoundException;
import com.zmo.bookmark.model.Bookmark;
import com.zmo.bookmark.model.Tag;
import com.zmo.bookmark.response.ResponseHandler;
import com.zmo.bookmark.service.BookmarkService;
import com.zmo.bookmark.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("bookmark/v1/bookmark")
public class BookmarkController {
    private final BookmarkService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getBookmarkById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BookmarkDTO.of(service.getBookmarkById(id)));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<Bookmark> bookmarks = service.findAll();

        return ResponseEntity.ok().body(ApiListResponse
                .builder()
                .results(BookmarkDTO.of(bookmarks))
                .totalCount(bookmarks.size())
                .build());
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> createBookmark(@RequestBody final BookmarkDTO dto) {
        service.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BookmarkDTO.of(service.getBookmarkByName(dto.getName())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyBookmark(@PathVariable Integer id, @RequestBody BookmarkDTO dto) {
        BookmarkDTO original = BookmarkDTO.of(service.getBookmarkById(id));
        BookmarkDTO updated = BookmarkDTO.of(service.update(id, dto));

        return ResponseHandler.responseBuilder(HttpStatus.OK, original, updated);
    }

    @PutMapping("/{bookmarkId}/{tagId}")
    public ResponseEntity<?> addTag(@PathVariable Integer bookmarkId, @PathVariable Integer tagId) {
        try {
            return ResponseEntity.ok().body(BookmarkDTO.of(service.addTag(bookmarkId, tagId)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().header("Message", "Not found").build();
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BookmarkDTO.of(service.getBookmarkById(bookmarkId)));
        }
    }

    @GetMapping("/getTags/{tagName}")
    public void getTags(@PathVariable String tagName){
        service.getBookmarksByTag(tagName);

    }



}
