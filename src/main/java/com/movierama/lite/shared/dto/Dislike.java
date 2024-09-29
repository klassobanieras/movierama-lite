package com.movierama.lite.shared.dto;

public record Dislike(MovieramaUser user, MovieDto movie) implements ReactionDto {
}
