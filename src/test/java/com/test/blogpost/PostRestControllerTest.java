package com.test.blogpost;

import com.google.common.collect.Sets;
import com.test.blogpost.controller.PostRestController;
import com.test.blogpost.entity.Post;
import com.test.blogpost.entity.User;
import com.test.blogpost.repository.PostRepository;
import com.test.blogpost.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
public class PostRestControllerTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostRestController postRestController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(postRestController).build();
    }

    @Test
    public void userNotExistsTest() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get("/users/" + userId + "/posts"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void getPostById() throws Exception {
        Long userId = 1L;
        Long postId = 2L;
        User user = new User();
        Post post1 = new Post();
        Post post2 = new Post();

        post1.setId(1L);
        post1.setVoteCount(0);
        post1.setUrl("text1");
        post1.setPostName("post2");

        post2.setId(2L);
        post2.setVoteCount(-1);
        post2.setUrl("text2");
        post2.setPostName("name2");

        user.setId(1L);
        user.setPosts(Sets.newHashSet(post1, post2));

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        when(postRepository.findById(2L)).thenReturn(java.util.Optional.ofNullable(post2));

        mockMvc.perform(get("/users/" + userId + "/posts"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
