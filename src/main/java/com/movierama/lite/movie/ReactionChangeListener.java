package com.movierama.lite.movie;

import com.movierama.lite.shared.events.ReactionAddedEvent;
import com.movierama.lite.shared.events.ReactionRemovedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class ReactionChangeListener {

    private final MovieRepository movieRepository;

    public ReactionChangeListener(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @EventListener
    public void handleReactionAddedEvent(ReactionAddedEvent event) {
        switch (event.getReactionType()){
            case LIKE -> movieRepository.incrementLikedCount(event.getMovieId());
            case DISLIKE -> movieRepository.incrementDislikedCount(event.getMovieId());
        }
    }

    @EventListener
    public void handleReactionRemovedEvent(ReactionRemovedEvent event) {
        switch (event.getReactionType()){
            case LIKE -> movieRepository.decrementLikedCount(event.getMovieId());
            case DISLIKE -> movieRepository.decrementDislikedCount(event.getMovieId());
        }
    }
}
