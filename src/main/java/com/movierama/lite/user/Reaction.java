package com.movierama.lite.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;

@Table(name = "reactions")
public record Reaction(@Id Long id,
                      @Column("user_id") Long userId,
                      @Column("movie_id") Long movieId,
                      ReactionType reactionType,
                      @Column(value = "creation_date") Instant creationDate) {

    public Reaction(Long movieId, Long userId, ReactionType reactionType) {
        this(null, userId, movieId, reactionType, Instant.now());
    }

    public static Reaction like(Long movieId, Long userId) {
        return new Reaction(movieId, userId, ReactionType.LIKE);
    }

    public static Reaction dislike(Long movieId, Long userId) {
        return new Reaction(movieId, userId, ReactionType.DISLIKE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reaction reaction)) return false;
        return Objects.equals(userId, reaction.userId) && Objects.equals(movieId, reaction.movieId);
    }
}

