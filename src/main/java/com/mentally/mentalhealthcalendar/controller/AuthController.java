package com.mentally.mentalhealthcalendar.controller;

import com.mentally.mentalhealthcalendar.exceptions.EmailAlreadyExistsException;
import com.mentally.mentalhealthcalendar.model.AppUser;
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

    @Autowired
    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping(path = "/signup")
    public Object signup(@RequestBody AppUser newUser) throws Throwable {
        boolean alreadyExists = appUserService.checkIfExistsByEmail(newUser.getEmail());

        if (alreadyExists) {
            return "User with email " + newUser.getEmail() + " already exists." ;
        } else {
            appUserService.addAppUser(newUser);
            return appUserService.findUserByEmail(newUser.getEmail());
        }
    }
}
