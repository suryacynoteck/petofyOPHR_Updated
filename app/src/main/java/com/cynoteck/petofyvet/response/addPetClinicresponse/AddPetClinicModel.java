package com.cynoteck.petofyvet.response.addPetClinicresponse;

public class AddPetClinicModel {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+"]";
    }

}
