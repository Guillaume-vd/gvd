package com.example.website.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

public class controller {

    @RestController
    public class TestController {

        @GetMapping(path = "/")
        @ResponseStatus(HttpStatus.OK)
        Response index() {
            return new Response();
        }

        @Data
        public class Response {
            public Integer id = 1;
        }
    }
}
