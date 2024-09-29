package com.movierama.lite.shared.dto;

import com.movierama.lite.reaction.ReactionType;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface MovieramaUser extends UserDetails {

    Map<Long, ReactionType> reactions();

    Long id();
}
