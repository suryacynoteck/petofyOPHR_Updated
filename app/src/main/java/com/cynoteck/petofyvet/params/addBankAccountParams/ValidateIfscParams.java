package com.cynoteck.petofyvet.params.addBankAccountParams;

public class ValidateIfscParams {
    private String ifsc;

    public String getIfsc ()
    {
        return ifsc;
    }

    public void setIfsc (String ifsc)
    {
        this.ifsc = ifsc;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ifsc = "+ifsc+"]";
    }
}
