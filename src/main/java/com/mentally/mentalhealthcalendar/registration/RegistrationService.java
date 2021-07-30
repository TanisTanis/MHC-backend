package com.mentally.mentalhealthcalendar.registration;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.model.AppUserRole;
import com.mentally.mentalhealthcalendar.service.AppUserService;
import com.mentally.mentalhealthcalendar.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AuthorizationService authorizationService;
    private final EmailValidator emailValidator;

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
}
