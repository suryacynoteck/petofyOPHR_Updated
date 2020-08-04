package com.cynoteck.petofyvet.params.getPetListRequest;

import com.cynoteck.petofyvet.params.forgetPassRequest.ForgetPassDataParams;

public class GetPetListRequest {
    private GetPetListParams data;

    public GetPetListParams getData() {
        return data;
    }

    public void setData(GetPetListParams data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
