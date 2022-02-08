package com.example.movierama.ui.controller;

import com.example.movierama.movie.adapter.in.web.UserController;
import com.example.movierama.persistence.domain.UserDto;
import com.example.movierama.user.domain.SignupUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MVCUsersController {

    private final UserController userController;


    @GetMapping(value = "/user/login")
    public String loginForm(ModelMap model) {
        model.addAttribute("action", "login");
        model.addAttribute("user", new UserDto());
        return "user-login-register";
    }

    @PostMapping(value = "/user/login")
    public String login(ModelMap model) {
        return "redirect:/movies";
    }

    @GetMapping("/user/signup")
    public ModelAndView signUpForm(SignupUserDto userDto, BindingResult bindingResult, ModelMap model) {
        model.addAttribute("action", "signup");
        model.addAttribute("user", new SignupUserDto());
        return new ModelAndView("user-login-register", model);
    }

    @PostMapping("/user/signup")
    public ModelAndView signUp(@Valid SignupUserDto userDto, BindingResult bindingResult,
                               ModelMap model) {
        //Validation
        List<String> errMsgs = bindingResult.getAllErrors().stream()
                .map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        if (!userDto.getPassword().equals(userDto.getPasswordRetype())) {
            final String errorMessage = "Passwords not matching..";
            bindingResult.addError(new FieldError("error", "error", errorMessage));
            errMsgs.add(errorMessage);
        }
        model.addAttribute("errorList", errMsgs);

        if (bindingResult.hasErrors()) {
            return signUpForm(userDto, bindingResult, model);
        }

        //Submitting
        try {
            userController.registerUser(userDto);
        } catch (Exception e) {
            bindingResult.addError(new FieldError("error", "error", "User already exist.."));
            return signUpForm(userDto, bindingResult, model);
        }

        model.remove("action");
        return new ModelAndView("redirect:/login", model);
    }


    @GetMapping("/login")
    public ModelAndView viewLoginPage(SignupUserDto userDto, BindingResult bindingResult, ModelMap model) {
        model.addAttribute("user", new SignupUserDto());
        return new ModelAndView("user-login-register", model);
    }

}
