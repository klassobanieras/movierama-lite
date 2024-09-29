package com.movierama.lite.reaction;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface ReactionRepository extends CrudRepository<Reaction, Long> {
    @Modifying
    @Query("DELETE FROM reactions WHERE user_id = :userId AND movie_id= :movieId and reaction_type = :reactionType")
    void deleteByUserIdAndMovieIdAndReactionType(@Param("userId") Long userId, @Param("movieId") Long movieId, @Param("reactionType") ReactionType reactionType);
}
