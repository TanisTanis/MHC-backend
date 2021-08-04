package com.mentally.mentalhealthcalendar.registration;

public class ResponseUser {
    private final Long id;
    private final String email;
    private final String token;

    public ResponseUser(Long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
