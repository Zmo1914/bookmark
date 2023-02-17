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
public class TagDTO {

    private Integer id;
    private String tagName;
    private List<String> bookmarks;

    public static Tag to(TagDTO dto) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(dto, tag);

        if (!CollectionUtils.isEmpty(dto.getBookmarks())) {
            dto.getBookmarks().forEach(b -> tag.getTaggedBookmarks().add(
                    Bookmark.
                            builder()
                            .name(b)
                            .linkedTags(Set.of(tag))
                            .build()));
        }

        return tag;
    }

    public static List<Tag> to(final List<TagDTO> dtoList) {
        return dtoList
                .stream()
                .map(TagDTO::to)
                .collect(Collectors.toList());
    }

    public static TagDTO of(final Tag tag) {
        TagDTO dto = new TagDTO();
        BeanUtils.copyProperties(tag, dto);

        if (!CollectionUtils.isEmpty(tag.getTaggedBookmarks())) {
            dto.setBookmarks(tag
                    .getTaggedBookmarks()
                    .stream()
                    .map(Bookmark::getName)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static List<TagDTO> of(final List<Tag> tags) {
        return tags
                .stream()
                .map(TagDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "{" +
                "\n\"id\":" + "\n\"" + id + "\"," +
                "\n\"tagName\":" + "\"" + tagName + "\"," +
                "\n}";
    }


}
