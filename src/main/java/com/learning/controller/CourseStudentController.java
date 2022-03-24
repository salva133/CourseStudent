package com.learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseStudentController {

    public CourseStudentController() {
    }

    @GetMapping(value = "do-something")
    public String doSomething() {
        return "doSomething executed";
    }

}
