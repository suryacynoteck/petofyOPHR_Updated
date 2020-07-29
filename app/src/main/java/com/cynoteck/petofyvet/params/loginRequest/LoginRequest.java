package com.cynoteck.petofyvet.params.loginRequest;

public class LoginRequest {
    private String Email;

    private String Password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Email = "+Email+", Password = "+Password+"]";
    }
}
