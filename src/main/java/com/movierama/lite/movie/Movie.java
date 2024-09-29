package com.movierama.lite.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(name = "movies")
public record Movie(
        @Id Long id,
        @Column(value = "title") String title,
        @Column(value = "description") String description,
        @Column(value = "liked_count") Long likedCount,
        @Column(value = "disliked_count") Long dislikedCount,
        @Column(value = "added_by") Long addedByUserId,
        @Column(value = "creation_date") Instant creationDate) {

    public Movie(String name, String description, Long addedByUserId) {
        this(null, name, description, 0L, 0L, addedByUserId, Instant.now());
    }
}