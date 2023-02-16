package com.zmo.bookmark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false)
    private Integer id;

    @Column(name = "tag_name", nullable = false, length = 50)
    private String tagName;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @Builder.Default
    @ManyToMany(mappedBy = "linkedTags")
    private Set<Bookmark> taggedBookmarks = new HashSet<>();



}