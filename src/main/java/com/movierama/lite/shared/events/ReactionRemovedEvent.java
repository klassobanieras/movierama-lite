package com.movierama.lite.shared.events;

import com.movierama.lite.user.ReactionType;
import org.springframework.context.ApplicationEvent;

public class ReactionRemovedEvent extends ApplicationEvent {

    private final Long movieId;
    private final ReactionType reactionType;

    public ReactionRemovedEvent(Object source, Long movieId, ReactionType reactionType) {
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
