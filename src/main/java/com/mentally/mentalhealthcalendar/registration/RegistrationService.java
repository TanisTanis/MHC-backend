package com.mentally.mentalhealthcalendar.registration;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.model.AppUserRole;
import com.mentally.mentalhealthcalendar.registration.token.ConfirmationToken;
import com.mentally.mentalhealthcalendar.registration.token.ConfirmationTokenService;
import com.mentally.mentalhealthcalendar.service.AppUserService;
import com.mentally.mentalhealthcalendar.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AuthorizationService authorizationService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest newUser) {
        boolean validEmail = emailValidator.test(newUser.getEmail());

        if (!validEmail) {
            throw new IllegalStateException("Email not valid");
        }
        return authorizationService.signUpUser(
                new AppUser(
                        newUser.getFirstName(),
                        newUser.getLastName(),
                        newUser.getEmail(),
                        newUser.getPassword(),
                        newUser.getPhone(),
                        AppUserRole.USER
                )
        );
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
        return "confirmed";
    }
}
