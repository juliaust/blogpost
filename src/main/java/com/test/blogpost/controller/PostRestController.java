package com.test.blogpost.controller;

import com.test.blogpost.Util;
import com.test.blogpost.cache.PostCache;
import com.test.blogpost.entity.Post;
import com.test.blogpost.entity.User;
import com.test.blogpost.exception.PostNotFoundException;
import com.test.blogpost.repository.PostRepository;
import com.test.blogpost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/posts")
public class PostRestController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostCache cache;

    @RequestMapping(method = RequestMethod.GET)
    Collection<Post> readPosts(@PathVariable Long userId) {
        User user = Util.validateUserExists(userId, userRepository);
        return this.postRepository.findByUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{postId}")
    Post readPost(@PathVariable Long userId, @PathVariable Long postId) {
        Util.validateUserExists(userId, userRepository);
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<Post> createPost(@PathVariable Long userId, @RequestBody Post body) {
        User user = Util.validateUserExists(userId, userRepository);
        Post post = new Post();
        post.setUser(user);
        post.setUrl(body.getUrl());
        post.setPostName(body.getPostName());
        post.setCreated(Util.convertToDateViaInstant(LocalDateTime.now()));
        post.setUpdated(Util.convertToDateViaInstant(LocalDateTime.now()));
        Post createdPost = postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{postId}", produces = "application/json", consumes = "application/json")
    ResponseEntity<Post> updatePost(@PathVariable Long userId, @PathVariable Long postId, @RequestBody Post body) {
        Post post = Util.validateUserPerm(userId, postId, postRepository);
        post.setUrl(body.getUrl());
        post.setPostName(body.getPostName());
        post.setUpdated(Util.convertToDateViaInstant(LocalDateTime.now()));
        Post updatedPost = postRepository.save(post);
        return ResponseEntity.ok(updatedPost);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/{postId}")
    ResponseEntity<?> deletePost(@PathVariable Long userId, @PathVariable Long postId) {
        User user = Util.validateUserExists(userId, userRepository);
        Post post = Util.validateUserPerm(userId, postId, postRepository);
        Set<Post> posts = user.getPosts();
        posts.remove(post);
        Set<Post> upvotedPosts = user.getUpvotedPosts();
        upvotedPosts.remove(post);
        Set<Post> downvotedPosts = user.getDownvotedPosts();
        downvotedPosts.remove(post);
        user.setPosts(posts);
        user.setUpvotedPosts(upvotedPosts);
        user.setDownvotedPosts(downvotedPosts);
        userRepository.saveAndFlush(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
