package com.zmo.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zmo.bookmark.model.Bookmark;
import com.zmo.bookmark.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BookmarkDTO {

    private Integer bookmarkId;
    private String name;
    private String path;
    private String description;

    private List<String> tags;

    public static Bookmark to(BookmarkDTO dto) {
        Bookmark bookmark = new Bookmark();
        BeanUtils.copyProperties(dto, bookmark);

        if (!CollectionUtils.isEmpty(dto.getTags())) {
            dto.getTags().forEach(t -> bookmark.getLinkedTags()
                    .add(Tag.builder()
                            .tagName(t)
                            .taggedBookmarks(Set.of(bookmark)).build()));
        }

        return bookmark;
    }

    public static List<Bookmark> to(final List<BookmarkDTO> dtoList) {
        return dtoList
                .stream()
                .map(BookmarkDTO::to)
                .collect(Collectors.toList());
    }

    public static BookmarkDTO of(final Bookmark bookmark) {
        BookmarkDTO dto = new BookmarkDTO();
        BeanUtils.copyProperties(bookmark, dto);

        if (!CollectionUtils.isEmpty(bookmark.getLinkedTags())) {
            dto.setTags(bookmark.getLinkedTags().stream().map(Tag::getTagName)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static List<BookmarkDTO> of(final List<Bookmark> bookmarks) {
        return bookmarks
                .stream()
                .map(BookmarkDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "{" +
                "\n\"bookmarkId\":" + "\n\"" + bookmarkId + "\"," +
                "\n\"name\":" + "\"" + name + "\"," +
                "\n\"path\":" + "\"" + path + "\"," +
                "\n\"description\":" + "\"" + description + "\"" +
                "\n}";
    }
}
