package com.cynoteck.petofyvet.params.newPetEntryParams;

import com.cynoteck.petofyvet.params.checkpetInVetRegister.InPetregisterParams;

public class NewPetRequest {

    private NewPetParams data;

    public NewPetParams getData ()
    {
        return data;
    }

    public void setData (NewPetParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }

}
