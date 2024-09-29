package com.movierama.lite.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u.* FROM users u WHERE username= :username")
    Optional<User> findByUsername(@Param("username") String username);
}
