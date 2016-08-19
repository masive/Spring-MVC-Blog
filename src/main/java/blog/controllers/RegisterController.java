package blog.controllers;

import blog.forms.RegisterForm;
import blog.services.NotificationService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/users/register")
    public String register(RegisterForm registerForm) {
        return "users/register";
    }

    @RequestMapping(value = "users/register", method = RequestMethod.POST)
    public String registerPage(
            @Valid RegisterForm registerForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notificationService.addErrorMessage("Please fill the form correctly!");
            return "users/register";
        }

        if (!userService.authenticate(registerForm.getUsername(), registerForm.getPassword())) {
            notificationService.addErrorMessage("Try again!");
            return "users/register";
        }

        notificationService.addInfoMessage("Registration successful");
        return "redirect:/users/login";
    }
}
