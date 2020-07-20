package com.cynoteck.petofyvet.response.loginresponse;

public class LoginResponse {
    private Data data;

    private Response response;

    private Header header;

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }

    public Response getResponseLogin()
    {
        return response;
    }

    public void setResponseLogin(Response response)
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
        return "ClassPojo [data = "+ data +", response = "+ response +", header = "+header+"]";
    }

}
