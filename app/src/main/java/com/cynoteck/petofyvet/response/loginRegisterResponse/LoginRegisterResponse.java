package com.cynoteck.petofyvet.response.loginRegisterResponse;

public class LoginRegisterResponse {
    private ResponseData data;

    private Response response;

    private Header header;

    public ResponseData getData()
    {
        return data;
    }

    public void setData(ResponseData data)
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
