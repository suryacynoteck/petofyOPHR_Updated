package com.cynoteck.petofyvet.response.newPetResponse;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;
import com.cynoteck.petofyvet.response.updateProfileResponse.UserModel;

import java.util.List;

public class NewPetRegisterResponse {

    private AddNewPetregisterModel data;

    private Header header;

    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AddNewPetregisterModel getData() {
        return data;
    }

    public void setData(AddNewPetregisterModel data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +", response = "+ response +", header = "+header+"]";
    }

}
