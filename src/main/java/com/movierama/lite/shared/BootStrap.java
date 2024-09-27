package com.movierama.lite.shared;

import com.movierama.lite.MovieRamaLiteApplication;
import com.movierama.lite.movie.Movie;
import com.movierama.lite.movie.MovieRepository;
import com.movierama.lite.user.User;
import com.movierama.lite.user.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootStrap {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BootStrap(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    @EventListener(ApplicationReadyEvent.class)
    public void setup() {
        if (userRepository.count() == 0) {
            var user = userRepository.save(new User("john", bCryptPasswordEncoder.encode("12345"), "john@email.com"));
            var user2 = userRepository.save(new User("bea", "12345", "bea@email.com"));
            var movie = movieRepository.save(new Movie("movie 1", "a small description", user.id()));
            movieRepository.incrementLikedCount(movie.id());
            movieRepository.incrementLikedCount(movie.id());
            movieRepository.incrementLikedCount(movie.id());
            movieRepository.incrementLikedCount(movie.id());
            movieRepository.incrementLikedCount(movie.id());
            movieRepository.incrementDislikedCount(movie.id());
            movieRepository.incrementDislikedCount(movie.id());

            movieRepository.save(new Movie("movie 2", "a small description", user.id()));
            movieRepository.save(new Movie("movie 3", "a small description", user.id()));
            movieRepository.save(new Movie("movie 4", "a small description", user.id()));
            movieRepository.save(new Movie("movie 5", "a small description", user.id()));
            movieRepository.save(new Movie("movie 6", "a small description", user.id()));
            movieRepository.save(new Movie("movie 7", "a small description", user.id()));
            movieRepository.save(new Movie("movie 8", "a small description", user.id()));
            movieRepository.save(new Movie("movie 9", "a small description", user.id()));
            movieRepository.save(new Movie("movie 10", "a small description", user.id()));
            movieRepository.save(new Movie("movie 11", "a small description", user.id()));
        }
    }
}
