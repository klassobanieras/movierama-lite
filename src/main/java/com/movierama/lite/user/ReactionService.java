package com.movierama.lite.user;

import com.movierama.lite.shared.CustomUserDetails;
import com.movierama.lite.shared.events.ReactionAddedEvent;
import com.movierama.lite.shared.events.ReactionRemovedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    public ReactionService(ReactionRepository reactionRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.reactionRepository = reactionRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void like(Long movieId, CustomUserDetails user) {
        var existingReaction = Optional.ofNullable(user.reactions().get(movieId));
        if (existingReaction.isPresent()) {
            if (existingReaction.get() == ReactionType.LIKE) {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                applicationEventPublisher.publishEvent(new ReactionRemovedEvent(this, movieId, ReactionType.LIKE));
            } else {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.LIKE));
                user.reactions().put(movieId, ReactionType.LIKE);
                applicationEventPublisher.publishEvent(new ReactionRemovedEvent(this, movieId, ReactionType.DISLIKE));
                applicationEventPublisher.publishEvent(new ReactionAddedEvent(this, movieId, ReactionType.LIKE));
            }
        } else {
            reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.LIKE));
            user.reactions().put(movieId, ReactionType.LIKE);
            applicationEventPublisher.publishEvent(new ReactionAddedEvent(this, movieId, ReactionType.LIKE));
        }
    }

    public void dislike(Long movieId, CustomUserDetails user) {
        var existingReaction = Optional.ofNullable(user.reactions().get(movieId));
        if (existingReaction.isPresent()) {
            if (existingReaction.get() == ReactionType.DISLIKE) {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                applicationEventPublisher.publishEvent(new ReactionRemovedEvent(this, movieId, ReactionType.DISLIKE));
            } else {
                reactionRepository.deleteByUserIdAndMovieIdAndReactionType(user.id(), movieId, existingReaction.get());
                user.reactions().remove(movieId);
                reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.DISLIKE));
                user.reactions().put(movieId, ReactionType.DISLIKE);
                applicationEventPublisher.publishEvent(new ReactionRemovedEvent(this, movieId, ReactionType.LIKE));
                applicationEventPublisher.publishEvent(new ReactionAddedEvent(this, movieId, ReactionType.DISLIKE));
            }
        } else {
            reactionRepository.save(new Reaction(movieId, user.id(), ReactionType.DISLIKE));
            user.reactions().put(movieId, ReactionType.DISLIKE);
            applicationEventPublisher.publishEvent(new ReactionAddedEvent(this, movieId, ReactionType.DISLIKE));
        }
    }
}
