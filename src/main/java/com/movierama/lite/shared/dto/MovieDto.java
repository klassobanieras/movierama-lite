package com.movierama.lite.shared.dto;

public record MovieDto(Long id, String title, String description, Long likedCount, Long dislikedCount,
                       String username) {
}
