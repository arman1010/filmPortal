package com.example.university.repository;

import com.example.university.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findAllByNameContainsAndSurnameContains(String name, String surname);
}
