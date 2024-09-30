package com.movierama.lite.movie;

import com.movierama.lite.movie.MovieService.MovieOrder;
import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.shared.dto.MovieramaUser;
import com.movierama.lite.shared.security.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String createMovie(Model model) {
        model.addAttribute("movie", new MovieDto(null, null, null, 0L, 0L, null));
        return "create-movie";
    }

    @PostMapping("/movies")
    public String createMovie(@ModelAttribute MovieDto movie, @AuthenticationPrincipal User user) {
        movieService.createMovie(movie.title(), movie.description(), user);
        return "redirect:/";
    }

    @GetMapping("/")
    public String fetchMovies(Model model,
                              @RequestParam(value = "sortBy", defaultValue = "DATE") MovieOrder order,
                              @RequestParam(value = "htmx", defaultValue = "false") boolean htmx,
                              @AuthenticationPrincipal MovieramaUser user) {
        model.addAttribute("username", Optional.ofNullable(user).map(MovieramaUser::getUsername).orElse(""));
        model.addAttribute("userReactions", Optional.ofNullable(user == null ? null : user.reactions()).orElse(new HashMap<>()));
        model.addAttribute("movies", movieService.findAll(order));
        model.addAttribute("sortBy", order.name());
        if (htmx) {
            return "fragments/movie-container :: movieContainer";
        }
        return "index";
    }

    @GetMapping("/movies/{username}")
    public String fetchMoviesOfUser(@RequestParam(defaultValue = "DATE") MovieOrder order,
                                    @RequestParam(defaultValue = "false") boolean htmx, Model model,
                                    @PathVariable String username,
                                    @AuthenticationPrincipal MovieramaUser user) {
        model.addAttribute("username", Optional.ofNullable(user).map(MovieramaUser::getUsername).orElse(""));
        model.addAttribute("userReactions", Optional.ofNullable(user == null ? null : user.reactions()).orElse(new HashMap<>()));
        model.addAttribute("movies", movieService.findAllOfUser(order, username));
        model.addAttribute("sortBy", order);
        if (htmx) {
            return "fragments/movie-container :: movieContainer";
        }
        return "index";
    }

    public Optional<MovieDto> fetchMovieById(long id) {
        return movieService.findById(id);
    }
}
