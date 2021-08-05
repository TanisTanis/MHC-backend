package com.mentally.mentalhealthcalendar.login;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.security.PasswordEncoder;
import com.mentally.mentalhealthcalendar.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/auth/login")
@AllArgsConstructor
public class LoginController {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public Long login(@RequestBody LogInRequest logInRequest) throws Throwable {
        Optional<AppUser> user = appUserService.findUserByEmail(logInRequest.getEmail());
        if (user.isPresent()) {
            String encodedPassword = user.get().getPassword();
            boolean validPass = passwordEncoder.bCryptPasswordEncoder().matches(logInRequest.getPassword(), encodedPassword);
            if (!validPass) {
                throw new IllegalAccessException("Invalid Password");
            }
            if (user.get().getLocked()) {
                throw new IllegalAccessException("Account with email " + logInRequest.getEmail() + " has been locked.");
            }
            if (!user.get().getEnabled()) {
                throw new IllegalAccessException("Account has not been enabled yet. Please check your email to confirm.");
            }
            return user.get().getId();
        } else {
           throw new IllegalStateException(String.format("User with email %s not found", logInRequest.getEmail()));
        }
    }
}
