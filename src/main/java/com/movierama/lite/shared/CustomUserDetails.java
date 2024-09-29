package com.movierama.lite.shared;

import com.movierama.lite.user.ReactionType;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface CustomUserDetails extends UserDetails {

    Map<Long, ReactionType> reactions();

    Long id();
}
