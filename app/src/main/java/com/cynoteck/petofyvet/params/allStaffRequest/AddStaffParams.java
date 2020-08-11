package com.cynoteck.petofyvet.params.allStaffRequest;

public class AddStaffParams {
    private String encryptId;

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    private String confirmPassword;

    private String email;

    public String getEncryptId() {
        return encryptId;
    }

    public void setEncryptId(String encryptId) {
        this.encryptId = encryptId;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getPhoneNumber ()
    {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getConfirmPassword ()
    {
        return confirmPassword;
    }

    public void setConfirmPassword (String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [encryptId = "+encryptId+" ,firstName = "+firstName+", lastName = "+lastName+", password = "+password+", phoneNumber = "+phoneNumber+", confirmPassword = "+confirmPassword+", email = "+email+"]";
    }
}