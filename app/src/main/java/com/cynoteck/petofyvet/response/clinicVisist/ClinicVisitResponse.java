package com.cynoteck.petofyvet.response.clinicVisist;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetReportsTypeData;

import java.util.ArrayList;

public class ClinicVisitResponse {
    private ArrayList<ClinicVisitsModel> data;

    private Response response;

    private Header header;

    public ArrayList<ClinicVisitsModel> getData ()
    {
        return data;
    }

    public void setData (ArrayList<ClinicVisitsModel> data)
    {
        this.data = data;
    }

    public Response getResponse ()
    {
        return response;
    }

    public void setResponse (Response response)
    {
        this.response = response;
    }

    public Header getHeader ()
    {
        return header;
    }

    public void setHeader (Header header)
    {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", response = "+response+", header = "+header+"]";
    }
}
