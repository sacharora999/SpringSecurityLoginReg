package com.example.demologinregproj.controller;

import com.example.demologinregproj.dto.UserDto;
import com.example.demologinregproj.entity.User;
import com.example.demologinregproj.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuthController {


    private UserService userService;

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/register")
    public String registerUser(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDto user,
                           BindingResult result,
                           Model model){

        User existingUser = userService.findUserByEmailId(user.getEmail());
        if(existingUser != null){
            result.rejectValue("email", null, "User Email Already Exists");
        }

        if(result.hasErrors()){
            model.addAttribute("student", user);
            return "register";
        }

        userService.saveMyUser(user);
        return "redirect:/register?success";
    }


    @GetMapping("/users")
    public String getMyUsers(Model model){
        List<UserDto> dtolist = userService.findAllUsers();
        model.addAttribute("users", dtolist);
        return "users";
    }



}
