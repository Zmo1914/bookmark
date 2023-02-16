package com.emil.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.emil.bookmark.model.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
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

    public static Bookmark to (BookmarkDTO dto){
        Bookmark bookmark = new Bookmark();
        BeanUtils.copyProperties(dto, bookmark);

        return bookmark;
    }

    public static List<Bookmark> to(final List<BookmarkDTO>dtoList){
        return dtoList
                .stream()
                .map(BookmarkDTO::to)
                .collect(Collectors.toList());
    }

    public static BookmarkDTO of(final Bookmark bookmark){
        BookmarkDTO dto = new BookmarkDTO();
        BeanUtils.copyProperties(bookmark, dto);

        return dto;
    }

    public static List<BookmarkDTO> of(final List<Bookmark> bookmarks){
        return bookmarks
                .stream()
                .map(BookmarkDTO::of)
                .collect(Collectors.toList());
    }


}
