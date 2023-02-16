package com.zmo.bookmark.repository;

import com.zmo.bookmark.model.Bookmark;
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

    @Query( "select o from Bookmark o where o.bookmarkId in :bookmarkIds" )
    List<Bookmark> findByBookmarkIdIs(List<Integer> bookmarkIds);

    @Query(value = "SELECT tag_id FROM Bookmarks_Tags bc WHERE bc.bookmark_id = :bookmarkId", nativeQuery = true)
    List<Integer> findTagsByBookmarkId(@Param("bookmarkId") Integer bookmarkId);

    @Query(value = "SELECT bookmark_id FROM Bookmarks_Tags bc WHERE bc.tag_id = :tagId",
            nativeQuery = true)
    List<Integer> findBookmarksByTagId(@Param("tagId") Integer tagId);

}
