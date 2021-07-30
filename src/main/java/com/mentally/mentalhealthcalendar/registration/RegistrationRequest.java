package com.mentally.mentalhealthcalendar.registration;

import com.mentally.mentalhealthcalendar.model.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final AppUserRole appUserRole;
}
