package com.cynoteck.petofyvet.params.updateRequest;

public class updateParams {
    private updateRequest data;

    public com.cynoteck.petofyvet.params.updateRequest.updateRequest getUpdateRequest() {
        return data;
    }

    public void setUpdateRequest(com.cynoteck.petofyvet.params.updateRequest.updateRequest data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
