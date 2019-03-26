package com.example.university.controller;

import com.example.university.model.Genre;
import com.example.university.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/addGenre")
    public String addGenreView(){
        return "addGenre";
    }
    @PostMapping("/addGenre")
    public String addGenre(@ModelAttribute Genre genre) {
        genreRepository.save(genre);
        return "redirect:/adminPage";
    }

    @GetMapping("/deleteGenre")
    public String deleteGenre(@RequestParam("id") int id) {
        genreRepository.deleteById(id);
        return "redirect:/";
    }
}
