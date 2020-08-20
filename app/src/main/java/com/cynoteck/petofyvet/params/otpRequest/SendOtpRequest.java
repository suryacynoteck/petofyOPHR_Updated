package com.cynoteck.petofyvet.params.otpRequest;

import com.cynoteck.petofyvet.params.checkpetInVetRegister.InPetregisterParams;

public class SendOtpRequest {

    private SendOtpParameter data;

    public SendOtpParameter getData() {
        return data;
    }

    public void setData(SendOtpParameter data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
