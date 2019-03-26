package com.example.university.controller;

import com.example.university.model.Movie;
import com.example.university.model.User;
import com.example.university.model.UserType;
import com.example.university.repository.GenreRepository;
import com.example.university.repository.MovieRepository;
import com.example.university.repository.UserRepository;
import com.example.university.security.SpringUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class UserController {
    @Value("C:\\springHomeWork\\uploadImages")
    String imageUploadDir;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/adminPage")
    public String adminPage(ModelMap modelMap) {
        modelMap.addAttribute("movies", movieRepository.findAll());
        modelMap.addAttribute("genres", genreRepository.findAll());
        return "adminPage";
    }

    @GetMapping("/userPage")
    public String userPage(ModelMap modelMap) {
        modelMap.addAttribute("movies", movieRepository.findAll());
        modelMap.addAttribute("genres", genreRepository.findAll());
        return "userPage";
    }


    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal SpringUser springUser) {
        if (springUser.getUser().getUserType() == UserType.ADMIN) {
            return "redirect:/adminPage";
        } else if (springUser.getUser().getUserType() == UserType.USER) {
            return "redirect:/userPage";
        }
        return "redirect:/";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam(name = "picture") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File picture = new File(imageUploadDir + File.separator + fileName);
        file.transferTo(picture);
        user.setPicUrl(fileName);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType(UserType.USER);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerView() {
        return "register";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
}
