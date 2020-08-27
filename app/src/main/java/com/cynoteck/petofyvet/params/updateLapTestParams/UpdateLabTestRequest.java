package com.cynoteck.petofyvet.params.updateLapTestParams;

import com.cynoteck.petofyvet.params.addLabRequest.AddLabParams;

public class UpdateLabTestRequest {
    private UpdateLabTestParams data;

    public UpdateLabTestParams getAddPetParams() {
        return data;
    }

    public void setAddPetParams(UpdateLabTestParams data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
