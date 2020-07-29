package com.cynoteck.petofyvet.params.petBreedRequest;

import com.cynoteck.petofyvet.params.loginparams.LoginRequest;

public class BreedParams {
    private BreedRequest data;

    public BreedRequest getData() {
        return data;
    }

    public void setData(BreedRequest data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
