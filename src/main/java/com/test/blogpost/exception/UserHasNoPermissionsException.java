package com.test.blogpost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserHasNoPermissionsException extends RuntimeException{
    public UserHasNoPermissionsException(Long userId, Long postId) {
        super("User " + userId + " has no permissions to perform an action on post " + postId + ".");
    }
}
