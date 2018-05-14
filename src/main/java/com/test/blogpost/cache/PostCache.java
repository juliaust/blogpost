package com.test.blogpost.cache;

import com.test.blogpost.entity.Post;
import com.test.blogpost.repository.PostRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Math.toIntExact;

@Component
public class PostCache {
    @Autowired
    private PostRepository postRepository;

    private List<Post> currentPosts = Lists.newArrayList();

    private static final Integer MAX_CACHE_SIZE = 15;

    public List<Post> getCurrentPosts() {
        return currentPosts;
    }

    public void setCurrentPosts(List<Post> currentPosts) {
        this.currentPosts = currentPosts;
    }

    @Scheduled(fixedDelay = 10000)
    public void refreshQue() {
        Sort sort = new Sort(Sort.Direction.DESC, "voteCount", "updated");
        Integer postsNumber = toIntExact(postRepository.count());
        Integer lastIdx = postsNumber > MAX_CACHE_SIZE ? MAX_CACHE_SIZE - 1 : postsNumber - 1;
        List<Post> refreshedList = postRepository.findAll(sort).subList(0, lastIdx);
        currentPosts = refreshedList;
    }
}
