package com.movierama.lite.user;

import com.movierama.lite.MovieRamaLiteApplicationTests;
import com.movierama.lite.mothers.MovieMother;
import com.movierama.lite.mothers.UserMother;
import com.movierama.lite.movie.Movie;
import com.movierama.lite.movie.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class UserRepositoryTest extends MovieRamaLiteApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @BeforeEach
    void setUp() {
        User defaultUser = UserMother.createDefaultUser();
        User user = userRepository.save(defaultUser);
        Movie likedMovie = movieRepository.save(MovieMother.createMovieWithName(user, "liked movie"));
        user.likeMovie(likedMovie.id());
        Movie dislikedMovie = movieRepository.save(MovieMother.createMovieWithName(user, "disliked movie"));
        user.dislikeMovie(dislikedMovie.id());
        userRepository.save(user);
    }

    @Nested
    class FindByUsernameWithReactions {
        @Test
        void testHappyPath() {
            User user = userRepository.findByUsername("default").orElseThrow();
            Assertions.assertThat(user.reactions()).isNotEmpty().hasSize(2);
        }
    }
}