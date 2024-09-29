package com.movierama.lite.shared.security;

import com.movierama.lite.reaction.ReactionType;
import com.movierama.lite.shared.dto.MovieramaUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table(name = "users")
public record User(
        @Id Long id,
        @Column(value = "username") String username,
        @Column(value = "password") String password,
        @Column(value = "creation_date") Instant creationDate,
        @Transient Map<Long, ReactionType> reactions) implements MovieramaUser {

    public User(String username, String password) {
        this(null, username, password, Instant.now(), new HashMap<>());
    }

    @PersistenceCreator
    public User(Long id, String username, String password, Instant creationDate) {
        this(id, username, password, creationDate, new HashMap<>());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}