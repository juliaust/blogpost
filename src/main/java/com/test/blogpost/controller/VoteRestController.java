package com.test.blogpost.controller;

import com.test.blogpost.Util;
import com.test.blogpost.dto.PostDTO;
import com.test.blogpost.entity.Post;
import com.test.blogpost.entity.User;
import com.test.blogpost.exception.PostNotFoundException;
import com.test.blogpost.exception.UserHasNoPermissionsException;
import com.test.blogpost.repository.PostRepository;
import com.test.blogpost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/posts/{postId}")
public class VoteRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PutMapping(value = "/upvote")
    ResponseEntity<Object> upvotePost(@PathVariable Long userId, @PathVariable Long postId, @RequestBody PostDTO body) {
        User user = Util.validateUserExists(userId, userRepository);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        Set<Post> upvotedPosts = user.getUpvotedPosts();
        if(upvotedPosts.contains(post)) {
            throw new UserHasNoPermissionsException(userId, postId);
        }
        upvotedPosts.add(post);
        Set<Post> downvotedPosts = user.getDownvotedPosts();
        downvotedPosts.remove(post);

        user.setDownvotedPosts(downvotedPosts);
        user.setUpvotedPosts(upvotedPosts);

        post.setVoteCount(post.getVoteCount() + 1);
        post.setUpdated(Util.convertToDateViaInstant(LocalDateTime.now()));

        userRepository.save(user);
        postRepository.save(post);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/downvote")
    ResponseEntity<Object> downvotePost(@PathVariable Long userId, @PathVariable Long postId, @RequestBody PostDTO body) {
        User user = Util.validateUserExists(userId, userRepository);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        Set<Post> downvotedPosts = user.getDownvotedPosts();
        if(downvotedPosts.contains(post)) {
            throw new UserHasNoPermissionsException(userId, postId);
        }
        downvotedPosts.add(post);

        Set<Post> upvotedPosts = user.getUpvotedPosts();
        upvotedPosts.remove(post);

        user.setDownvotedPosts(downvotedPosts);
        user.setUpvotedPosts(upvotedPosts);

        post.setVoteCount(post.getVoteCount() - 1);
        post.setUpdated(Util.convertToDateViaInstant(LocalDateTime.now()));

        userRepository.save(user);
        postRepository.save(post);
        return ResponseEntity.ok().build();
    }
}
