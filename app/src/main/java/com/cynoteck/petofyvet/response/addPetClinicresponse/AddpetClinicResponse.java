package com.cynoteck.petofyvet.response.addPetClinicresponse;

import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicRequest;
import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;
import com.cynoteck.petofyvet.response.clinicVisist.ClinicVisitsModel;

import java.util.ArrayList;

public class AddpetClinicResponse {
    private AddPetClinicModel data;

    private Response response;

    private Header header;

    public AddPetClinicModel getData ()
    {
        return data;
    }

    public void setData (AddPetClinicModel data)
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
