package com.cynoteck.petofyvet.params.updateRequest;

public class updateParams {
    private updateRequest updateRequest;

    public com.cynoteck.petofyvet.params.updateRequest.updateRequest getUpdateRequest() {
        return updateRequest;
    }

    public void setUpdateRequest(com.cynoteck.petofyvet.params.updateRequest.updateRequest updateRequest) {
        this.updateRequest = updateRequest;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ updateRequest +"]";
    }
}
