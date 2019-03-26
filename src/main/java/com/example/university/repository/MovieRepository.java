package com.example.university.repository;

import com.example.university.model.Genre;
import com.example.university.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByGenres(Set<Genre> genres);
}
