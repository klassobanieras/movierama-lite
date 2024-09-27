package com.movierama.lite.shared.dto;

import java.util.Set;

public record UserDto(String username, Set<ReactionDto> reactions) {
}
