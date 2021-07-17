package com.dungnt.healthclinic.dto;

public class SignUpRequest {
    private String name;
    private String username;
    private String password;
    private String email;
    private String role;
    private String room;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignUpRequest(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
