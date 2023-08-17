package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Query("select user from User user join fetch user.roles role where user.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Override
    @Transactional
    @Query("select user from User user join fetch user.roles role where user.id = :id")
    Optional<User> findById(Long id);

}
