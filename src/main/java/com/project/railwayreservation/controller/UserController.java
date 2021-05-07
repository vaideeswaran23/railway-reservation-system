package com.project.railwayreservation.controller;

import com.project.railwayreservation.entities.User;
import com.project.railwayreservation.entities.UserLogin;
import com.project.railwayreservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User newUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (user.equals(newUser)) {
                System.out.println("User Already exists!");
                model.addAttribute("error", "User Already exists!");
                return "add-user";
            }
        }

        userRepository.save(newUser);
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@Valid UserLogin userLogin, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
        User newUser = userRepository.findByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (user.equals(newUser)) {
                user.setLoggedIn(true);
                userRepository.save(user);
                model.addAttribute("user", user);
                return "redirect:/train";
            }
        }

        model.addAttribute("error", "User not found!");
        return "redirect:/index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/logout")
    public String logUserOut() {
        List<User> users = userRepository.findAll();

        for (User other : users) {
            other.setLoggedIn(false);
            userRepository.save(other);
        }
        return "redirect:/index";

    }

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("userLogin", new UserLogin());
        return "index";
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("userLogin", new UserLogin());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userRepository.save(user);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/index";
    }



}
