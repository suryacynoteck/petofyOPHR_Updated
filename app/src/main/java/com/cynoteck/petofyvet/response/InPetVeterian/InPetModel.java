package com.cynoteck.petofyvet.response.InPetVeterian;

public class InPetModel {
    private String success;
    private String petId;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    @Override
    public String toString() {
        return "ClassPojo [success = "+success+", petId= "+petId+"]";

    }
}
