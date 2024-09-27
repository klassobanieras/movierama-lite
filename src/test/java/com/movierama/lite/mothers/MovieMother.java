package com.movierama.lite.mothers;

import com.movierama.lite.movie.Movie;
import com.movierama.lite.user.User;

public class MovieMother {
    public static Movie createDefaultMovie(User addedBy) {
        return new Movie("movie", "movie description", addedBy.id());
    }

    public static Movie createMovieWithName(User addedBy, String name) {
        return new Movie(name, "movie description",  addedBy.id());
    }
}
