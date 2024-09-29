package com.movierama.lite.user;

import com.movierama.lite.shared.CustomUserDetails;
import com.movierama.lite.shared.dto.Dislike;
import com.movierama.lite.shared.dto.Like;
import com.movierama.lite.shared.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Table(name = "users")
public record User(
        @Id Long id,
        @Column(value = "username") String username,
        @Column(value = "password") String password,
        @Column(value = "creation_date") Instant creationDate,
        Map<Long, ReactionType> reactions) implements CustomUserDetails {

    public User(String username, String password) {
        this(null, username, password, Instant.now(), new HashMap<>());
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