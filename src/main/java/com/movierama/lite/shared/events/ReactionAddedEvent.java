package com.movierama.lite.shared.events;

import com.movierama.lite.reaction.ReactionType;
import org.springframework.context.ApplicationEvent;

public class ReactionAddedEvent extends ApplicationEvent {

    private final Long movieId;
    private final ReactionType reactionType;

    public ReactionAddedEvent(Object source, Long movieId, ReactionType reactionType) {
        super(source);
        this.movieId = movieId;
        this.reactionType = reactionType;
    }

    public Long getMovieId() {
        return movieId;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }
}
