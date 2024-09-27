package com.movierama.lite.movie;

import com.movierama.lite.MovieRamaLiteApplicationTests;
import com.movierama.lite.mothers.MovieMother;
import com.movierama.lite.mothers.UserMother;
import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.user.User;
import com.movierama.lite.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MovieRepositoryTest extends MovieRamaLiteApplicationTests {

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

    @Test
    void testFetchAll() {
        Assertions.assertThat(movieRepository.findAll(10, 0, "createdAt")).hasSize(10).extracting(MovieDto::username).doesNotContainNull();
    }
}
