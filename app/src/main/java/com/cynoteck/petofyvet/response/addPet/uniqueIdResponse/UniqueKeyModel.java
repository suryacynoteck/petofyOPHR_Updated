package com.cynoteck.petofyvet.response.addPet.uniqueIdResponse;

public class UniqueKeyModel {

    private String petUniqueId;

    public String getPetUniqueId() {
        return petUniqueId;
    }

    public void setPetUniqueId(String petUniqueId) {
        this.petUniqueId = petUniqueId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petUniqueId = "+petUniqueId+"]";
    }

}

