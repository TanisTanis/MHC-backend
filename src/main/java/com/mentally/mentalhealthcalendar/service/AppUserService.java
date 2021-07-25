package com.mentally.mentalhealthcalendar.service;

import com.mentally.mentalhealthcalendar.exceptions.UserNotFoundException;
import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final UserRepo userRepo;

    @Autowired
    public AppUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public AppUser addAppUser(AppUser user) {
        return userRepo.save(user);
    }

    public List<AppUser> findAllUsers() {
        return userRepo.findAll();
    }

    public AppUser updateUser(AppUser user) {
        return userRepo.save(user);
    }

    public void deleteEmployee(Long id) {
        userRepo.deleteUserById(id);
    }

    public Optional<AppUser> findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public boolean checkIfExistsByEmail(String email) throws Throwable {
        return userRepo.findUserByEmail(email).isPresent();
    }

    public AppUser findAppUserById(Long id) throws Throwable {
        return (AppUser) userRepo.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }
}
