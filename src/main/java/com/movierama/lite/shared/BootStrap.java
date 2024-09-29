package com.movierama.lite.shared;

import com.movierama.lite.movie.MovieController;
import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.shared.security.User;
import com.movierama.lite.shared.security.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Component
public class BootStrap {

    private final UserRepository userRepository;
    private final MovieController movieController;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BootStrap(UserRepository userRepository, MovieController movieController) {
        this.userRepository = userRepository;
        this.movieController = movieController;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void setup() {
        if (userRepository.count() == 0) {
            var user = userRepository.save(new User("john", bCryptPasswordEncoder.encode("12345")));
            var user2 = userRepository.save(new User("bea", bCryptPasswordEncoder.encode("12345")));
            IntStream.range(1, 10)
                    .forEach(i -> movieController.createMovie(new MovieDto(null, "Movie title " + i, "A Description fairly accurate and big", 0L, 0L, "john"), user));
        }
    }
}
