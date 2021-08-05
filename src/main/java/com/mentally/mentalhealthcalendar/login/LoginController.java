package com.mentally.mentalhealthcalendar.login;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/auth/login")
@AllArgsConstructor
public class LoginController {

    private final AppUserService appUserService;

    @PostMapping
    public Long login(@RequestBody LogInRequest logInRequest) throws Throwable {
        Optional<AppUser> user = appUserService.findUserByEmail(logInRequest.getEmail());
        if (user.isPresent()) {
            return user.get().getId();
        } else {
           throw new IllegalStateException(String.format("User with email %s not found", logInRequest.getEmail()));
        }
    }
}
