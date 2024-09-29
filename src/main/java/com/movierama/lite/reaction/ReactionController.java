package com.movierama.lite.reaction;

import com.movierama.lite.movie.MovieController;
import com.movierama.lite.shared.dto.MovieramaUser;
import com.movierama.lite.shared.dto.Dislike;
import com.movierama.lite.shared.dto.Like;
import com.movierama.lite.shared.dto.MovieDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReactionController {

    private final ReactionService reactionService;
    private final MovieController movieController;

    public ReactionController(ReactionService reactionService, MovieController movieController) {
        this.reactionService = reactionService;
        this.movieController = movieController;
    }

    @PostMapping("/like/{movieId}")
    public String like(@PathVariable("movieId") Long movieId, @AuthenticationPrincipal MovieramaUser user, Model model) {
        MovieDto movie = movieController.fetchMovieById(movieId).orElseThrow(() -> new IllegalArgumentException("Failed to find movie to like"));
        if (movie.username().equals(user.getUsername())) throw new IllegalArgumentException("You cannot like your own movie");
        Like like = reactionService.like(new Like(user, movie));
        model.addAttribute("movie", like.movie());
        model.addAttribute("userReactions", like.user().reactions());
        return "fragments/like-dislike-section :: likeDislikeSection";
    }

    @PostMapping("/dislike/{movieId}")
    public String dislike(@PathVariable("movieId") Long movieId, @AuthenticationPrincipal MovieramaUser user, Model model) {
        MovieDto movie = movieController.fetchMovieById(movieId).orElseThrow(() -> new IllegalArgumentException("Failed to find movie to like"));
        if (movie.username().equals(user.getUsername())) throw new IllegalArgumentException("You cannot dislike your own movie");
        Dislike dislike = reactionService.dislike(new Dislike(user, movie));
        model.addAttribute("movie", dislike.movie());
        model.addAttribute("userReactions", dislike.user().reactions());
        return "fragments/like-dislike-section :: likeDislikeSection";
    }
}
