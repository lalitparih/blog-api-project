package com.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "title should not be less than 2 char")
    private String title;
    @NotEmpty
    @Size(min = 4, message = "description should not be lesss than 4 char")
    private String description;
    @NotEmpty(message = "content should not be empty")
    private String content;
}
