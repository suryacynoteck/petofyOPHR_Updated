package com.cynoteck.petofyvet.response.addPet.breedResponse;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;
import com.cynoteck.petofyvet.response.updateProfileResponse.CityModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BreedCatRespose {
    private Header header;

    @SerializedName("data")
    private List<BreedModel> data = null;

    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<BreedModel> getData() {
        return data;
    }

    public void setData(List<BreedModel> data) {
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
        return "ClassPojo [cityModel = "+ data +", response = "+ response +", header = "+header+"]";
    }
}
