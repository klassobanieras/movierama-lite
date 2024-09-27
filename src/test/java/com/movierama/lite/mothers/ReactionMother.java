package com.movierama.lite.mothers;

import com.movierama.lite.movie.Movie;
import com.movierama.lite.user.Reaction;
import com.movierama.lite.user.User;

public class ReactionMother {

    public static Reaction createLikeReactionOfUserOnMovie(User user, Movie movie) {
        return Reaction.like(movie.id(), user.id());
    }

    public static Reaction createDislikeReactionOfUserOnMovie(User user, Movie movie) {
        return Reaction.dislike(movie.id(), user.id());
    }
}
