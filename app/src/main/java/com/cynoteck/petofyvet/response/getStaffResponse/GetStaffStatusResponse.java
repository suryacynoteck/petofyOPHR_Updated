package com.cynoteck.petofyvet.response.getStaffResponse;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;

public class GetStaffStatusResponse {


    private Response response;

    private Header header;



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
        return "ClassPojo [ response = "+response+", header = "+header+"]";
    }
}
