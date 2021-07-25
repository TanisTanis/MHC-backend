package com.mentally.mentalhealthcalendar.repo;

import com.mentally.mentalhealthcalendar.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    void deleteUserById(Long id);
    Optional<AppUser> findUserById(Long id);
    Optional<AppUser> findUserByEmail(String email);
}
