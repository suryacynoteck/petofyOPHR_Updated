package com.cynoteck.petofyvet.params.updateRequest.getValue;

public class updateParams {
    private updateRequest data;

    public updateRequest getUpdateRequest() {
        return data;
    }

    public void setUpdateRequest(updateRequest data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
