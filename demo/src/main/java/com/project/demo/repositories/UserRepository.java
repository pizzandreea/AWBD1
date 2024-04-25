package com.project.demo.repositories;

import com.project.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    Optional<User> findOneByEmailAndPassword(String email, String password);
}
