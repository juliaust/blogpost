package com.test.blogpost.repository;

import com.test.blogpost.entity.Post;
import com.test.blogpost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Collection<Post> findByUser(User user);
}
