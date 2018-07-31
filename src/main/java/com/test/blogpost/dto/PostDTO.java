package com.test.blogpost.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class PostDTO {

    private Long id;

    @NonNull
    private String url;

    private String postName;

    private Integer voteCount;

    private Date created;

    private Date updated;
}
