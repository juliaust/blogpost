package com.test.blogpost.dto;

import com.test.blogpost.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {
    private long id;

    @NonNull
    private String localId;

    @NonNull
    private String name;

    private Set<Post> posts;

    private Set<Post> downvotedPosts;

    private Set<Post> upvotedPosts;
}
