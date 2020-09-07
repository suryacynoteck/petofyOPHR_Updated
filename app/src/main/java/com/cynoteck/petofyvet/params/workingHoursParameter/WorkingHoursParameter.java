package com.cynoteck.petofyvet.params.workingHoursParameter;

import com.cynoteck.petofyvet.params.updateXRayParams.UpdateXRayParams;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class WorkingHoursParameter {
    private JsonArray data=null;

    public JsonArray getData ()
    {
        return data;
    }

    public void setData (JsonArray data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
