package com.zmo.bookmark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
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

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "bookmarks_tags",
            joinColumns = @JoinColumn(name = "bookmark_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> linkedTags = new LinkedHashSet<>();

    public void addTag(Tag tag){
        linkedTags.add(tag);
        tag.getTaggedBookmarks().add(this);
    }

    public void removeTag(Tag tag){
        linkedTags.remove(tag);
        tag.getTaggedBookmarks().remove(this);
    }

}