package com.mentally.mentalhealthcalendar.registration;

import com.mentally.mentalhealthcalendar.email.EmailBuilder;
import com.mentally.mentalhealthcalendar.email.EmailSender;
import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.model.AppUserRole;
import com.mentally.mentalhealthcalendar.registration.token.ConfirmationToken;
import com.mentally.mentalhealthcalendar.registration.token.ConfirmationTokenService;
import com.mentally.mentalhealthcalendar.service.AppUserService;
import com.mentally.mentalhealthcalendar.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AuthorizationService authorizationService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final AppUserService appUserService;
    private final EmailBuilder emailBuilder;

    public ResponseUser register(RegistrationRequest newUser) {
        boolean validEmail = emailValidator.test(newUser.getEmail());

        if (!validEmail) {
            throw new IllegalStateException("Email not valid");
        }
        String token = authorizationService.signUpUser(
                new AppUser(
                        newUser.getFirstName(),
                        newUser.getLastName(),
                        newUser.getEmail(),
                        newUser.getPassword(),
                        newUser.getPhone(),
                        AppUserRole.USER
                )
        );
        String confirmationLink = "http://localhost:8080/api/auth/signup/confirm?token=" + token;
        emailSender.send(newUser.getEmail(), emailBuilder.buildEmail(newUser.getFirstName(), confirmationLink));
        AppUser addedUser = appUserService.findUserByEmail(newUser.getEmail()).orElseThrow(() -> new IllegalStateException("Account not found"));
        return new ResponseUser(addedUser.getId(), addedUser.getEmail(), token);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token is expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        authorizationService.enableAppUser(confirmationToken.getAppUser().getEmail());
        String returnLink = "<a href=\"https://localhost:4200/login\">here</a>";
        return "Your email has been confirmed! Please click " + returnLink + " and login with your credentials";
    }
}
