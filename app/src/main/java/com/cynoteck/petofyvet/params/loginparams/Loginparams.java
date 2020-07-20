package com.cynoteck.petofyvet.params.loginparams;



public class Loginparams {
    private Data data;
        public Data getLoginData()
    {
        return data;
    }

    public void setLoginData(Data data)
    {
        this.data = data;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
