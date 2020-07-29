package com.cynoteck.petofyvet.params.addParamRequest;

public class AddPetRequset {
    private AddPetParams addPetParams;

    public AddPetParams getAddPetParams() {
        return addPetParams;
    }

    public void setAddPetParams(AddPetParams addPetParams) {
        this.addPetParams = addPetParams;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ addPetParams +"]";
    }
}
