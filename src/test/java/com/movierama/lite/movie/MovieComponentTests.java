package com.movierama.lite.movie;

import com.movierama.lite.MovieRamaLiteApplicationTests;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MovieComponentTests extends MovieRamaLiteApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class CreateMovieTests {
        @Test
        @WithUserDetails("john")
        void happyPathCreateTest() throws Exception {
            mockMvc.perform(post("/movies").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Inception")
                    .param("description", "A mind-bending thriller")).andExpect(status().is3xxRedirection());
        }

        @Test
        @WithAnonymousUser
        void unAuthorizedFails() throws Exception {
            mockMvc.perform(post("/movies").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Inception")
                    .param("description", "A mind-bending thriller")).andExpect(status().is3xxRedirection());
        }
    }
}
