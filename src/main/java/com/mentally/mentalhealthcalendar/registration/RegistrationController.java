package com.mentally.mentalhealthcalendar.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping(path="api/auth/signup")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String signup(@RequestBody RegistrationRequest newUser) {
//        String token = registrationService.register(newUser);
//        return new ResponseEntity<>(token, HttpStatus.OK);
        //look at response entity return type
        return registrationService.register(newUser);
    }

    @GetMapping(path = "/confirm")
    public String confirmToken(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
