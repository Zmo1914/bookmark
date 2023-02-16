package com.emil.bookmark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookmark")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id", nullable = false)
    private Integer bookmarkId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "path", nullable = false, length = 150)
    private String path;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @Version
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

}