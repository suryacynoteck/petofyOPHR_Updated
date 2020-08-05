package com.cynoteck.petofyvet.response.addPet.imageUpload;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;
import com.cynoteck.petofyvet.response.addPet.breedResponse.BreedModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {
    private Header header;

    private ImageResponseModel data;

    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ImageResponseModel getData() {
        return data;
    }

    public void setData(ImageResponseModel data) {
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
        return "ClassPojo [imageResponse = "+ data +", response = "+ response +", header = "+header+"]";
    }
}
