package com.project.railwayreservation.entities;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class UserLogin {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserLogin() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLogin)) return false;
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(getEmail(), userLogin.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }
}
