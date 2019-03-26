package com.example.university.controller;

import com.example.university.model.Genre;
import com.example.university.model.Movie;
import com.example.university.model.User;
import com.example.university.repository.GenreRepository;
import com.example.university.repository.MovieRepository;
import com.example.university.security.SpringUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class MovieController {
    @Value("C:\\springHomeWork\\uploadImages")
    String imageUploadDir;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/addMovie")
    public String addMovieView(ModelMap modelMap) {
        modelMap.addAttribute("genres", genreRepository.findAll());
        return "addMovie";
    }

    @PostMapping("/addMovie")
    public String addMovie(@ModelAttribute Movie movie, @RequestParam(name = "picture") MultipartFile file, @AuthenticationPrincipal SpringUser springUser) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imageUploadDir + File.separator + fileName);
        file.transferTo(picture);
        movie.setPicUrl(fileName);
        movie.setDate(new Date());
        movie.setUser(springUser.getUser());
        movieRepository.save(movie);
        return "redirect:/adminPage";
    }

    @GetMapping("/deleteMovie")
    public String deleteMovie(@RequestParam("id") int id) {
        movieRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/currentMovie")
    public String currentMovie(ModelMap modelMap, @RequestParam("id") int id) {
        Movie one = movieRepository.getOne(id);
        modelMap.addAttribute("genres", genreRepository.findAll());
        modelMap.addAttribute("movie", one);
        return "currentMovie";
    }

    @GetMapping("/moviesByGenre")
    public String getMoviesByGenreId(@RequestParam("id") int id, ModelMap modelMap) {
        Genre genre = genreRepository.getOne(id);
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
        modelMap.addAttribute("genre", genre);
        modelMap.addAttribute("movies", movieRepository.findAllByGenres(genres));
        return "moviesByGenre";
    }

}
