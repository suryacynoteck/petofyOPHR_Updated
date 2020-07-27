package com.cynoteck.petofyvet.params.getPetRequest;

public class GetPetDataRequest {
    private GetPetDataParams getPetDataParams;

    public GetPetDataParams getData ()
    {
        return getPetDataParams;
    }

    public void setData (GetPetDataParams data)
    {
        this.getPetDataParams = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+getPetDataParams+"]";
    }

}