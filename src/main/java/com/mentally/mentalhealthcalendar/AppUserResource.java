package com.mentally.mentalhealthcalendar;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserResource {
    private final AppUserService appUserService;

    @Autowired
    public AppUserResource(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> appUsers = appUserService.findAllUsers();
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AppUser> getUser(@PathVariable("id") Long id) throws Throwable {
        AppUser appUser = appUserService.findAppUserById(id);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user) {
        AppUser appUser = appUserService.addAppUser(user);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user) {
        AppUser appUser = appUserService.updateUser(user);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id) {
        appUserService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
