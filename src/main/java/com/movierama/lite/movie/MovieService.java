package com.movierama.lite.movie;

import com.movierama.lite.shared.dto.MovieDto;
import com.movierama.lite.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(String name, String description, User addedBy) {
        var movie = new Movie(name, description, addedBy.id());
        movieRepository.save(movie);
    }

    public Page<MovieDto> findAll(int page, int size, MovieOrder orderBy) {
        return new PageImpl<>(movieRepository.findAll(size, page * size, orderBy.column()), PageRequest.of(page, size), movieRepository.count());
    }

    public Page<MovieDto> findAllOfUser(int page, int size, MovieOrder orderBy, String addedByUsername) {
        return new PageImpl<>(movieRepository.findAllOfUser(addedByUsername, size, page * size, orderBy.column()), PageRequest.of(page, size), movieRepository.count());
    }

    public MovieDto findById(long id) {
        return movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    public enum MovieOrder {
        LIKES("likesCount"),
        HATES("hatesCount"),
        DATE("createdAt");

        private final String column;

        MovieOrder(String column) {
            this.column = column;
        }

        public String column() {return column;}
    }
}
