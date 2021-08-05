package com.mentally.mentalhealthcalendar.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LogInRequest {
    private final String email;
    private final String password;
}
