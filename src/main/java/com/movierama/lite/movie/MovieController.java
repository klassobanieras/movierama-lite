package com.movierama.lite.movie;

import com.movierama.lite.movie.MovieService.MovieOrder;
import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.user.User;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "DATE") MovieOrder order,
                              @RequestParam(defaultValue = "false") boolean htmx) {
        int pageSize = 10;
        Page<MovieDto> moviePage = movieService.findAll(page, pageSize, order);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        if (htmx) {
            return "fragments/movie-cards :: movieCards";
        }
        return "index";
    }

    @GetMapping("/users/{username}/movies")
    public String fetchMoviesOfUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "DATE") MovieOrder order, @RequestParam(defaultValue = "false") boolean htmx, @PathParam("username") String username, Model model) {
        int pageSize = 10;
        Page<MovieDto> moviePage = movieService.findAllOfUser(page, pageSize, order, username);
        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        if (htmx) {
            return "fragments/movie-cards :: movieCards";
        }
        return "index";
    }

    public MovieDto fetchMovieById(long id) {
        return movieService.findById(id);
    }
}
