package com.movierama.lite.shared.dto;

public record MovieDto(Long id, String title, String description, Long likedCount, Long dislikedCount,
                       String username) {
    public MovieDto withRemoveLike() {
        return new MovieDto(id, title, description, likedCount -1, dislikedCount, username);
    }

    public MovieDto withRemoveDislike() {
        return new MovieDto(id, title, description, likedCount, dislikedCount -1, username);
    }

    public MovieDto withAddLike() {
        return new MovieDto(id, title, description, likedCount +1, dislikedCount, username);
    }

    public MovieDto withAddDislike() {
        return new MovieDto(id, title, description, likedCount, dislikedCount +1, username);
    }
}
