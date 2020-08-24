package com.cynoteck.petofyvet.params.addLabRequest;

import com.cynoteck.petofyvet.params.addParamRequest.AddPetParams;

public class AddLabRequest {
    private AddLabParams data;

    public AddLabParams getAddPetParams() {
        return data;
    }

    public void setAddPetParams(AddLabParams data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
