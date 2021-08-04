package com.mentally.mentalhealthcalendar.service;

import com.mentally.mentalhealthcalendar.email.EmailBuilder;
import com.mentally.mentalhealthcalendar.email.EmailService;
import com.mentally.mentalhealthcalendar.model.AppUser;
import com.mentally.mentalhealthcalendar.registration.token.ConfirmationToken;
import com.mentally.mentalhealthcalendar.registration.token.ConfirmationTokenService;
import com.mentally.mentalhealthcalendar.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UserRepo appUserRepo;
    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final EmailBuilder emailBuilder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(AppUser newUser) {
        boolean userExists = appUserRepo.findUserByEmail(newUser.getEmail()).isPresent();
        if (userExists) {
            AppUser existingUser = appUserRepo.findUserByEmail(newUser.getEmail()).orElseThrow(() -> new IllegalStateException("User with email " + newUser
                    .getEmail() + " does not exist"));
            boolean enabled = existingUser.getEnabled();
            if (enabled) {
                throw new IllegalStateException(String.format("User with email %s already exists. Please log in instead.", newUser.getEmail()));
            } else {
                String newToken = UUID.randomUUID().toString();
                ConfirmationToken newConfirmationToken = new ConfirmationToken(
                        newToken,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        existingUser
                );
                confirmationTokenService.saveConfirmationToken(newConfirmationToken);
                String confirmationLink = "http://localhost:8080/api/auth/signup/confirm?token=" + newToken;
                emailService.send(existingUser.getEmail(), emailBuilder.buildEmail(existingUser.getFirstName(), confirmationLink));
                throw new IllegalStateException(String.format("Account with email %s already exists, but is not enabled. Another confirmation has been sent to your email, please click the link and confirm to continue.", existingUser.getEmail()));
            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);

        appUserRepo.save(newUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                newUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepo.enableAppUser(email);
    }
}
