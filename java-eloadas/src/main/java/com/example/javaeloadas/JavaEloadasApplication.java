package com.example.javaeloadas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@Controller
public class JavaEloadasApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(JavaEloadasApplication.class, args);
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("exampleAttribute", 123123123);
        return "index";
    }
}