package com.zmo.bookmark.repository;

import com.zmo.bookmark.model.Bookmark;
import com.zmo.bookmark.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    boolean existsBookmarkByNameIgnoreCase(String name);

    Optional<Bookmark> findBookmarkByNameIgnoreCase(String name);

    @Query("SELECT bookmark.linkedTags " +
            "FROM Bookmark bookmark " +
            "JOIN  Tag " +
            "WHERE bookmark.bookmarkId = :bookmarkId")
    List<Tag> getAllTags(@Param("bookmarkId") Integer bookmarkId);
}
