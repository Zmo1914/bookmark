package com.zmo.bookmark.repository;

import com.zmo.bookmark.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepisotory extends JpaRepository<Bookmark, Integer> {
    Optional<Bookmark> findAllByEnabledIsTrue();

    boolean existsBookmarkByBookmarkId(Integer bookmarkId);

    boolean existsBookmarkByBookmarkIgnoreCase(String name);
}

