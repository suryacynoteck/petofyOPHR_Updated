package com.cynoteck.petofyOPHR.response.searchDiagnosisResponse;

import com.cynoteck.petofyOPHR.response.Header;
import com.cynoteck.petofyOPHR.response.Response;

import java.util.List;

public class SearchDiagnosisResponseData {
    private Header header;
    private List<SearchDiagnosisResponseModel> data = null;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<SearchDiagnosisResponseModel> getData() {
        return data;
    }

    public void setData(List<SearchDiagnosisResponseModel> data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "header= " + header +
                ", data= " + data +
                ", response= " + response +
                "]";
    }
}
