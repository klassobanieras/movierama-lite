package com.movierama.lite.reaction;

import com.movierama.lite.reaction.Reaction;
import com.movierama.lite.reaction.ReactionRepository;
import com.movierama.lite.reaction.ReactionService;
import com.movierama.lite.reaction.ReactionType;
import com.movierama.lite.shared.dto.Dislike;
import com.movierama.lite.shared.dto.Like;
import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.shared.dto.MovieramaUser;
import com.movierama.lite.shared.events.ReactionAddedEvent;
import com.movierama.lite.shared.events.ReactionRemovedEvent;
import com.movierama.lite.shared.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ReactionServiceTest {

    @Mock
    private ReactionRepository reactionRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @InjectMocks
    private ReactionService reactionService;
    private MovieramaUser user;
    private MovieDto movie;

    @BeforeEach
    public void setUp() {
        user = new User("userId", "pass");
        movie = new MovieDto(1L, "movieId", "Inception", 0L, 0L, "joe");
        user.reactions().clear();
    }

    @Test
    public void testLike_WhenNoExistingReaction_ShouldAddLike() {
        Like like = new Like(user, movie);
        Like result = reactionService.like(like);
        verify(reactionRepository).save(any(Reaction.class));
        verify(applicationEventPublisher).publishEvent(any(ReactionAddedEvent.class));
        assertEquals(ReactionType.LIKE, result.user().reactions().get(movie.id()));
    }

    @Test
    public void testDislike_WhenNoExistingReaction_ShouldAddDislike() {
        Dislike dislike = new Dislike(user, movie);
        Dislike result = reactionService.dislike(dislike);
        verify(reactionRepository).save(any(Reaction.class));
        verify(applicationEventPublisher).publishEvent(any(ReactionAddedEvent.class));
        assertEquals(ReactionType.DISLIKE, result.user().reactions().get(movie.id()));
    }

    @Test
    public void testLike_WhenExistingDislike_ShouldRemoveDislikeAndAddLike() {
        Like like = new Like(user, movie);
        user.reactions().put(movie.id(), ReactionType.DISLIKE);
        Like result = reactionService.like(like);
        verify(reactionRepository).deleteByUserIdAndMovieIdAndReactionType(user.id(), movie.id(), ReactionType.DISLIKE);
        verify(applicationEventPublisher).publishEvent(any(ReactionRemovedEvent.class));
        verify(reactionRepository).save(any(Reaction.class));
        verify(applicationEventPublisher).publishEvent(any(ReactionAddedEvent.class));
        assertEquals(ReactionType.LIKE, result.user().reactions().get(movie.id()));
    }

    @Test
    public void testDislike_WhenExistingLike_ShouldRemoveLikeAndAddDislike() {
        Dislike dislike = new Dislike(user, movie);
        user.reactions().put(movie.id(), ReactionType.LIKE);
        Dislike result = reactionService.dislike(dislike);verify(reactionRepository).deleteByUserIdAndMovieIdAndReactionType(user.id(), movie.id(), ReactionType.LIKE);
        verify(applicationEventPublisher).publishEvent(any(ReactionRemovedEvent.class));
        verify(reactionRepository).save(any(Reaction.class));
        verify(applicationEventPublisher).publishEvent(any(ReactionAddedEvent.class));
        assertEquals(ReactionType.DISLIKE, result.user().reactions().get(movie.id()));
    }

    @Test
    public void testLike_WhenExistingLike_ShouldRemoveLike() {
        user.reactions().put(movie.id(), ReactionType.LIKE);
        Like like = new Like(user, movie);
        Like result = reactionService.like(like);
        verify(reactionRepository).deleteByUserIdAndMovieIdAndReactionType(user.id(), movie.id(), ReactionType.LIKE);
        verify(applicationEventPublisher).publishEvent(any(ReactionRemovedEvent.class));
        assertNull(result.user().reactions().get(movie.id()));
    }

    @Test
    public void testDislike_WhenExistingDislike_ShouldRemoveDislike() {
        user.reactions().put(movie.id(), ReactionType.DISLIKE);
        Dislike dislike = new Dislike(user, movie);
        Dislike result = reactionService.dislike(dislike);
        verify(reactionRepository).deleteByUserIdAndMovieIdAndReactionType(user.id(), movie.id(), ReactionType.DISLIKE);
        verify(applicationEventPublisher).publishEvent(any(ReactionRemovedEvent.class));
        assertNull(result.user().reactions().get(movie.id()));
    }
}