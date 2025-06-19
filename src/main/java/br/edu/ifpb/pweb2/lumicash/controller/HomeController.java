package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller 
public class HomeController {

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("pages/home"); 
    }
}
