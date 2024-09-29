package com.movierama.lite.movie;

import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.user.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;
    private final JdbcClient jdbcClient;

    public MovieService(MovieRepository movieRepository, JdbcClient jdbcClient) {
        this.movieRepository = movieRepository;
        this.jdbcClient = jdbcClient;
    }

    public void createMovie(String name, String description, User addedBy) {
        var movie = new Movie(name, description, addedBy.id());
        movieRepository.save(movie);
    }

    public List<MovieDto> findAll(MovieOrder orderBy) {
        return jdbcClient.sql("""
                        SELECT m.id, m.title, m.description, m.liked_count, m.disliked_count, u.username
                        FROM movies m
                            LEFT JOIN users u on u.id = m.added_by
                        ORDER BY 
                        """ + orderBy.column + " DESC")
                .query(MovieDto.class).list();
    }

    public List<MovieDto> findAllOfUser(MovieOrder orderBy, String addedByUsername) {
        return jdbcClient.sql("""
                        SELECT m.id, m.title, m.description, m.liked_count, m.disliked_count, u.username
                        FROM movies m
                            LEFT JOIN users u on u.id = m.added_by
                        WHERE u.username = :addedByUsername
                        ORDER BY 
                        """ + orderBy.column + " DESC")
                .param("addedByUsername", addedByUsername)
                .query(MovieDto.class).list();
    }

    public MovieDto findById(long id) {
        return movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    public enum MovieOrder {
        LIKES("m.liked_count"),
        DISLIKES("m.disliked_count"),
        DATE("m.creation_date");

        private final String column;

        MovieOrder(String column) {
            this.column = column;
        }

        public String column() {
            return column;
        }
    }
}
