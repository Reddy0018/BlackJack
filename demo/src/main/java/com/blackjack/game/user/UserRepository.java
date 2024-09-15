package com.blackjack.game.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * from users u where u.email= :email LIMIT 1", nativeQuery = true)
    User findByEmail(String email);
}
