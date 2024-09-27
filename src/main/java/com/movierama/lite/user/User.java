package com.movierama.lite.user;

import com.movierama.lite.shared.dto.Dislike;
import com.movierama.lite.shared.dto.Like;
import com.movierama.lite.shared.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "users")
public record User(
        @Id Long id,
        @Column(value = "username") String username,
        @Column(value = "password") String password,
        @Column(value = "email") String email,
        @Column(value = "creation_date") Instant creationDate,
        @MappedCollection(idColumn = "user_id") Set<Reaction> reactions) {

    public User(String username, String password, String email) {
        this(null, username, password, email, Instant.now(), new HashSet<>());
    }

    public boolean likeMovie(Long movieId) {
        Reaction like = Reaction.like(movieId, id());
        return reactions().add(like);
    }

    public boolean dislikeMovie(Long movieId) {
        Reaction dislike = Reaction.dislike(movieId, id());
        return reactions().add(dislike);
    }

    public UserDto toDto() {
        return new UserDto(username(), reactions().stream().map(reaction -> reaction.reactionType() == ReactionType.LIKE ? new Like(reaction.movieId()) : new Dislike(reaction.movieId())).collect(Collectors.toSet()));
    }
}