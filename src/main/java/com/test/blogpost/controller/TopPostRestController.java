package com.test.blogpost.controller;

import com.test.blogpost.cache.PostCache;
import com.test.blogpost.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/top")
public class TopPostRestController {

    @Autowired
    private PostCache cache;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Post>> getTopPosts() {
        return ResponseEntity.ok(cache.getCurrentPosts());
    }
}
