package com.cynoteck.petofyvet.params.addBankAccountParams;

public class ValidateIfscParams {
    private String ifscCode;

    public String getIfscCode ()
    {
        return ifscCode;
    }

    public void setIfscCode (String ifscCode)
    {
        this.ifscCode = ifscCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ifscCode = "+ifscCode+"]";
    }
}
