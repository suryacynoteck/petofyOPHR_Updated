package com.cynoteck.petofyvet.response.bankAccountResponse;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;

import java.util.ArrayList;

public class GetBankAccoutsResponse {
    private ArrayList<GetBankAccoutsData> data;

    private Response response;

    private Header header;

    public ArrayList<GetBankAccoutsData> getData ()
    {
        return data;
    }

    public void setData (ArrayList<GetBankAccoutsData> data)
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
