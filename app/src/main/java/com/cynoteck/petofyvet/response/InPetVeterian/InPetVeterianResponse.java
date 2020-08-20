package com.cynoteck.petofyvet.response.InPetVeterian;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffData;

import java.util.ArrayList;

public class InPetVeterianResponse {
    private InPetModel data;

    private Response response;

    private Header header;

    public InPetModel getData ()
    {
        return data;
    }

    public void setData (InPetModel data)
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
