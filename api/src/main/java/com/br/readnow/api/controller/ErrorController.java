package com.br.readnow.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "redirect:/404";
    }

    @RequestMapping("/404")
    public String handleError() {
        return "404";
    }
}

