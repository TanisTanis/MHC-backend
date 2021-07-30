package com.mentally.mentalhealthcalendar.repo;

import com.mentally.mentalhealthcalendar.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    void deleteUserById(Long id);
    Optional<AppUser> findUserById(Long id);
    Optional<AppUser> findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
