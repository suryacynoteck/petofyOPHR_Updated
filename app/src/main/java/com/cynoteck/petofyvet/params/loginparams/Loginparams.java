package com.cynoteck.petofyvet.params.loginparams;

public class Loginparams {
    private LoginRequest data;
        public LoginRequest getLoginData()
    {
        return data;
    }

    public void setLoginData(LoginRequest data)
    {
        this.data = data;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
