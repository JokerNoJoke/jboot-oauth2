package com.example.oauth2.resource;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping
    public Object index(Authentication authentication) {
        return authentication.getDetails();
    }

    @GetMapping("/authentication")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

}
