package com.movierama.lite.user;

import com.movierama.lite.movie.MovieRepository;
import com.movierama.lite.shared.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final MovieRepository movieRepository;

    public ReactionService(ReactionRepository reactionRepository, MovieRepository movieRepository) {
        this.reactionRepository = reactionRepository;
        this.movieRepository = movieRepository;
    }

    public void like(Long movieId, CustomUserDetails user) {
        var existingReaction = Optional.ofNullable(user.reactions().get(movieId));
        if (existingReaction.isPresent()) {
            if (existingReaction.get() == ReactionType.LIKE) {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                movieRepository.decrementLikedCount(movieId);
            } else {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.LIKE));
                user.reactions().put(movieId, ReactionType.LIKE);
                movieRepository.decrementDislikedCount(movieId);
                movieRepository.incrementLikedCount(movieId);
            }
        } else {
            reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.LIKE));
            user.reactions().put(movieId, ReactionType.LIKE);
            movieRepository.incrementLikedCount(movieId);
        }
    }

    public void dislike(Long movieId, CustomUserDetails user) {
        var existingReaction = Optional.ofNullable(user.reactions().get(movieId));
        if (existingReaction.isPresent()) {
            if (existingReaction.get() == ReactionType.DISLIKE) {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                movieRepository.decrementDislikedCount(movieId);
            } else {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.DISLIKE));
                user.reactions().put(movieId, ReactionType.DISLIKE);
                movieRepository.decrementLikedCount(movieId);
                movieRepository.incrementDislikedCount(movieId);
            }
        } else {
            reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.DISLIKE));
            user.reactions().put(movieId, ReactionType.DISLIKE);
            movieRepository.incrementDislikedCount(movieId);
        }
    }
}
