package com.movierama.lite.movie;

import com.movierama.lite.movie.MovieService.MovieOrder;
import com.movierama.lite.shared.CustomUserDetails;
import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movies")
    public void createMovie(String title, String description, @AuthenticationPrincipal User user) {
        movieService.createMovie(title, description, user);
    }

    @GetMapping("/")
    public String fetchMovies(Model model,
                              @RequestParam(value = "sortBy", defaultValue = "DATE") MovieOrder order,
                              @RequestParam(value = "htmx", defaultValue = "false") boolean htmx,
                              @AuthenticationPrincipal CustomUserDetails user) {
        model.addAttribute("userReactions", Optional.ofNullable(user == null ? null : user.reactions()).orElse(new HashMap<>()));
        model.addAttribute("movies", movieService.findAll(order));
        model.addAttribute("sortBy", order.name());
        if (htmx) {
            return "fragments/movie-container :: movieContainer";
        }
        return "index";
    }

    @GetMapping("/movies/{username}")
    public String fetchMoviesOfUser(@RequestParam(defaultValue = "DATE") MovieOrder order, @RequestParam(defaultValue = "false") boolean htmx, Model model, @PathVariable String username, @AuthenticationPrincipal CustomUserDetails user) {
        model.addAttribute("userReactions", Optional.ofNullable(user == null ? null : user.reactions()).orElse(new HashMap<>()));
        model.addAttribute("movies", movieService.findAllOfUser(order, username));
        model.addAttribute("sortBy", order);
        if (htmx) {
            return "fragments/movie-container :: movieContainer";
        }
        return "index";
    }

    public MovieDto fetchMovieById(long id) {
        return movieService.findById(id);
    }
}
