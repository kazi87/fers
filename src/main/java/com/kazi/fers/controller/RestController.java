package com.kazi.fers.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kacper.zielinski on 29.11.2015.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/some")
    public String index2() {
        return "Greetings from Spring Boot!";
    }


}
