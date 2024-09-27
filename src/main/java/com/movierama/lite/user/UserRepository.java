package com.movierama.lite.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("""
            SELECT u.* FROM users u WHERE username=:username
            """)
    Optional<User> findByUsername(@Param("username") String username);

    @Query("select u.email, u.password, u.id, u.username from users u where email = :email")
    Optional<SecurityUser> findByEmail(@Param("email") String email);

    record SecurityUser(String email, String password, Long id, String username) implements UserDetails {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return email;
        }
    }
}
