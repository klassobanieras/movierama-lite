package com.movierama.lite.reaction;

import com.movierama.lite.shared.dto.MovieramaUser;
import com.movierama.lite.shared.dto.Dislike;
import com.movierama.lite.shared.dto.Like;
import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.shared.dto.ReactionDto;
import com.movierama.lite.shared.events.ReactionAddedEvent;
import com.movierama.lite.shared.events.ReactionRemovedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
class ReactionService {

    private final ReactionRepository reactionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    public ReactionService(ReactionRepository reactionRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.reactionRepository = reactionRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Like like(Like like) {
        var existingReaction = Optional.ofNullable(like.user().reactions().get(like.movie().id()));
        if (existingReaction.isEmpty()) {
            like = (Like) addNewReaction(like);
        } else {
            like = (Like) removeExistingReaction(like, existingReaction.get());
            if (existingReaction.get() == ReactionType.DISLIKE) {
                like = (Like) addNewReaction(like);
            }
        }
        return like;
    }



    public Dislike dislike(Dislike dislike) {
        var existingReaction = Optional.ofNullable(dislike.user().reactions().get(dislike.movie().id()));
        if (existingReaction.isEmpty()) {
            dislike = (Dislike) addNewReaction(dislike);
        } else {
            dislike = (Dislike) removeExistingReaction(dislike, existingReaction.get());
            if (existingReaction.get() == ReactionType.LIKE) {
                dislike = (Dislike) addNewReaction(dislike);
            }
        }
        return dislike;
    }

    private ReactionDto addNewReaction(ReactionDto reaction) {
        switch (reaction) {
            case Like(MovieramaUser user, MovieDto movie) -> {
                reactionRepository.save(new Reaction(movie.id(), user.id(), ReactionType.LIKE));
                applicationEventPublisher.publishEvent(new ReactionAddedEvent(this, movie.id(), ReactionType.LIKE));
                user.reactions().put(movie.id(), ReactionType.LIKE);
                movie = movie.withAddLike();
                return new Like(user, movie);
            }
            case Dislike(MovieramaUser user, MovieDto movie) -> {
                reactionRepository.save(new Reaction(movie.id(), user.id(), ReactionType.DISLIKE));
                applicationEventPublisher.publishEvent(new ReactionAddedEvent(this, movie.id(), ReactionType.DISLIKE));
                user.reactions().put(movie.id(), ReactionType.DISLIKE);
                movie = movie.withAddDislike();
                return new Dislike(user, movie);
            }
        }
    }

    private ReactionDto removeExistingReaction(ReactionDto newReaction, ReactionType existingReaction) {
        reactionRepository.deleteByUserIdAndMovieIdAndReactionType(newReaction.user().id(), newReaction.movie().id(), existingReaction);
        applicationEventPublisher.publishEvent(new ReactionRemovedEvent(this, newReaction.movie().id(), existingReaction));
        newReaction.user().reactions().remove(newReaction.movie().id());
        var movie = switch (existingReaction) {
            case LIKE -> newReaction.movie().withRemoveLike();
            case DISLIKE -> newReaction.movie().withRemoveDislike();
        };
        switch (newReaction) {
            case Like(MovieramaUser user, MovieDto _) -> {
                return new Like(user, movie);
            }
            case Dislike(MovieramaUser user, MovieDto _) -> {
                return new Dislike(user, movie);
            }
        }
    }
}
