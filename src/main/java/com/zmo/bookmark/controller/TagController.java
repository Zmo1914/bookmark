package com.zmo.bookmark.controller;

import com.zmo.bookmark.dto.ApiListResponse;
import com.zmo.bookmark.dto.TagDTO;
import com.zmo.bookmark.model.Tag;
import com.zmo.bookmark.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("bookmark/v1/tag")
public class TagController {

    private final TagService service;

    @PostMapping("/")
    public ResponseEntity<?> createTag(@RequestBody TagDTO dto) {
        return ResponseEntity.ok().body(TagDTO.of(service.create(TagDTO.to(dto))));
    }

    @GetMapping("/{tagName}")
    public ResponseEntity<?> findByName(@PathVariable @NotNull String tagName){
        return ResponseEntity.ok().body(TagDTO.of(service.findByName(tagName)));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<Tag> tags = service.findAll();

        return ResponseEntity.ok().body(ApiListResponse.builder()
                .results(TagDTO.of(tags))
                .totalCount(tags.size()).build());
    }

//    public ResponseEntity<?> getBookmarksByTag(Integer tagId){
//
//    }
}
