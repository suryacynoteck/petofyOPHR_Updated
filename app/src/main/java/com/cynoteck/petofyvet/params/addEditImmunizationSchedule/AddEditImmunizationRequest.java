package com.cynoteck.petofyvet.params.addEditImmunizationSchedule;

public class AddEditImmunizationRequest {
    private AddEditImmunizationParams data;

    public AddEditImmunizationParams getData ()
    {
        return data;
    }

    public void setData (AddEditImmunizationParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
