package com.example.workforcemgmt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String seyHello() {
        return "Hello iam ";
    }
    
}
