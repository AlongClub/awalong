package org.alongclub.awalong.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestful {
    @GetMapping("/user/hello")
    public String hello(){
        return "Hello World!";
    }
}
