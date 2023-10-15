package com.webapp.Rahgeer.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RahgeerController {

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("msg", "");
        return "login";
    }

    @GetMapping("/signupTourist")
    public String signupTourist() {
        return "signupTourist";
    }

    @GetMapping("/signupHost")
    public String signupHost() {
        return "signupHost";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
