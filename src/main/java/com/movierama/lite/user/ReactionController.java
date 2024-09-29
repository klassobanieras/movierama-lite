package com.movierama.lite.user;

import com.movierama.lite.movie.MovieRepository;
import com.movierama.lite.shared.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReactionController {

    private final ReactionRepository reactionRepository;
    private final MovieRepository movieRepository;
    private final ReactionService reactionService;

    public ReactionController(ReactionRepository reactionRepository, MovieRepository movieRepository, ReactionService reactionService) {
        this.reactionRepository = reactionRepository;
        this.movieRepository = movieRepository;
        this.reactionService = reactionService;
    }

    @PostMapping("/like/{movieId}")
    public String like(@PathVariable("movieId") Long movieId, @AuthenticationPrincipal CustomUserDetails user, Model model) {
        movieRepository.findById(movieId).orElseThrow();
        reactionService.like(movieId, user);
        var movie = movieRepository.findById(movieId).orElseThrow();
        model.addAttribute("movie", movie);
        model.addAttribute("userReactions", user.reactions());
        return "fragments/like-dislike-section :: likeDislikeSection";
    }

    @PostMapping("/dislike/{movieId}")
    public String dislike(@PathVariable("movieId") Long movieId, @AuthenticationPrincipal CustomUserDetails user, Model model) {
        movieRepository.findById(movieId).orElseThrow();
        reactionService.dislike(movieId, user);
        var movie = movieRepository.findById(movieId).orElseThrow();
        model.addAttribute("movie", movie);
        model.addAttribute("userReactions", user.reactions());
        return "fragments/like-dislike-section :: likeDislikeSection";
    }

}
