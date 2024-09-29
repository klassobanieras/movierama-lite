package com.movierama.lite.movie;

import com.movierama.lite.shared.dto.MovieDto;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface MovieRepository extends ListCrudRepository<Movie, Long> {

    @Query("""
    SELECT m.id, m.title, m.description, m.liked_count, m.disliked_count, u.username
    FROM movies m
        LEFT JOIN public.users u on u.id = m.added_by
    WHERE m.id = :id
    """)
    Optional<MovieDto> findById(@Param("id") long id);

    @Modifying
    @Query("UPDATE movies SET liked_count = liked_count + 1 WHERE id = :id")
    void incrementLikedCount(@Param("id") long id);

    @Modifying
    @Query("UPDATE movies SET disliked_count = disliked_count + 1 WHERE id = :id")
    void incrementDislikedCount(@Param("id") long id);

    @Modifying
    @Query("UPDATE movies SET liked_count = liked_count - 1 WHERE id = :id")
    void decrementLikedCount(@Param("id") long id);

    @Modifying
    @Query("UPDATE movies SET disliked_count = disliked_count - 1 WHERE id = :id")
    void decrementDislikedCount(@Param("id") long id);
}
