package com.mentally.mentalhealthcalendar.registration;

import com.mentally.mentalhealthcalendar.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/auth/signup")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public Object signup(@RequestBody RegistrationRequest newUser) throws Throwable {
        return registrationService.register(newUser);
    }

    @GetMapping(path = "/confirm")
    public String confirmToken(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
