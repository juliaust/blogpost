package com.test.blogpost;

import com.test.blogpost.entity.Post;
import com.test.blogpost.entity.User;
import com.test.blogpost.exception.PostNotFoundException;
import com.test.blogpost.exception.UserHasNoPermissionsException;
import com.test.blogpost.exception.UserNotFoundException;
import com.test.blogpost.repository.PostRepository;
import com.test.blogpost.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Util {
    private Util(){};

    public static User validateUserExists(Long userId, UserRepository userRepository) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public static Post validateUserPerm(Long userId, Long postId, PostRepository postRepository) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        if(post.getUser() == null
                || post.getUser().getId() == null
                || !post.getUser().getId().equals(userId))  {
            throw new UserHasNoPermissionsException(userId, postId);
        }
        return post;
    }

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
