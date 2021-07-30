package com.mentally.mentalhealthcalendar.service;

import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UserRepo appUserRepo;
    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(AppUser newUser) {
        boolean userExists = appUserRepo.findUserByEmail(newUser.getEmail()).isPresent();
        if (userExists) {
            return String.format("User With Email %s Already Exists", newUser.getEmail());
        }

        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);

        appUserRepo.save(newUser);
        return encodedPassword;
    }
}
