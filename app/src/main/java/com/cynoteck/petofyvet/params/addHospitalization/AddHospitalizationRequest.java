package com.cynoteck.petofyvet.params.addHospitalization;

import com.cynoteck.petofyvet.params.addTestXRayParams.AddTestXRayParams;

public class AddHospitalizationRequest {
    private AddHospitalizationParam data;

    public AddHospitalizationParam getData ()
    {
        return data;
    }

    public void setData (AddHospitalizationParam data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}

