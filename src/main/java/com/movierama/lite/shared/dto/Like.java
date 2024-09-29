package com.movierama.lite.shared.dto;

public record Like(MovieramaUser user, MovieDto movie) implements ReactionDto {
}
