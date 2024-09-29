package com.movierama.lite.shared.security;

import com.movierama.lite.reaction.ReactionType;
import com.movierama.lite.shared.dto.MovieramaUser;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JdbcClient jdbcClient;

    public UserService(UserRepository userRepository, JdbcClient jdbcClient) {
        this.userRepository = userRepository;
        this.jdbcClient = jdbcClient;
    }

    @Override
    public MovieramaUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return jdbcClient
                .sql("select u.id, u.username, u.password, u.creation_date, r.movie_id,r.reaction_type from users u left join reactions r on r.user_id = u.id where u.username = ?")
                .param(username).query((ResultSetExtractor<Optional<User>>) rs -> {
                    if (!rs.next()) {
                        return Optional.empty();
                    }
                    var reactions = new HashMap<Long, ReactionType>();
                    User user = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).toInstant(), reactions);
                    do {
                        String reactionName = rs.getString(6);
                        if (reactionName != null) {
                            reactions.put(rs.getLong(5), ReactionType.valueOf(reactionName));
                        }
                    } while (rs.next());
                    return Optional.of(user);
                }).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public MovieramaUser saveUser(MovieramaUser user) {
        return userRepository.save((User) user);
    }
}
