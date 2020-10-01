package com.cynoteck.petofyvet.params.addImmunizationClinic;

import com.cynoteck.petofyvet.params.addLabRequest.AddLabParams;

public class ImmunizationClinicData {
    private ImmunizationAddClinicModel data;

    public ImmunizationAddClinicModel getAddPetParams() {
        return data;
    }

    public void setAddPetParams(ImmunizationAddClinicModel data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
