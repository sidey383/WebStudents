package ru.sidey383.webstudents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {


    @RequestMapping("/")
    public String groupsPage() {
        return "/index.html";
    }

    @RequestMapping("/group/{id}")
    public String studentsPage() {
        return "/group.html";
    }

}
