package com.mentally.mentalhealthcalendar.controller;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.registration.RegistrationRequest;
import com.mentally.mentalhealthcalendar.registration.RegistrationService;
import com.mentally.mentalhealthcalendar.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AppUserService appUserService;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(AppUserService appUserService, RegistrationService registrationService) {
        this.appUserService = appUserService;
        this.registrationService = registrationService;
    }

    @PostMapping(path = "/signup")
    public Object signup(@RequestBody RegistrationRequest newUser) throws Throwable {
        boolean alreadyExists = appUserService.checkIfExistsByEmail(newUser.getEmail());

//        if (alreadyExists) {
//            return "User with email " + newUser.getEmail() + " already exists." ;
//        } else {
//            appUserService.addAppUser(newUser);
//            return appUserService.findUserByEmail(newUser.getEmail());
//        }
        return registrationService.register(newUser);
    }

}
