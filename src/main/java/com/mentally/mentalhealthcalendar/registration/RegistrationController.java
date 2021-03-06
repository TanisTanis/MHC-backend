package com.mentally.mentalhealthcalendar.registration;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="api/auth/signup")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<ResponseUser> signup(@RequestBody RegistrationRequest newUser) {
        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseUser user = registrationService.register(newUser);
        return new ResponseEntity<>(user, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping(path = "/confirm")
    public String confirmToken(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return "confirmed";
    }

    @PostMapping(path="/resendConfirmation")
    public ResponseEntity<ResponseUser> resendEmail(@RequestBody String email) {
        ResponseUser user = registrationService.resendEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
