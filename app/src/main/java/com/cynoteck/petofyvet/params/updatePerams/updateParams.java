package com.cynoteck.petofyvet.params.updatePerams;

import com.cynoteck.petofyvet.params.loginparams.LoginRequest;

public class updateParams {
    private updateRequest updateRequest;

    public com.cynoteck.petofyvet.params.updatePerams.updateRequest getUpdateRequest() {
        return updateRequest;
    }

    public void setUpdateRequest(com.cynoteck.petofyvet.params.updatePerams.updateRequest updateRequest) {
        this.updateRequest = updateRequest;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ updateRequest +"]";
    }
}
