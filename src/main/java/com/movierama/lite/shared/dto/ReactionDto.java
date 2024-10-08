package com.movierama.lite.shared.dto;

public sealed interface ReactionDto permits Dislike, Like {
    MovieramaUser user();
    MovieDto movie();
}
