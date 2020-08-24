package com.cynoteck.petofyvet.response.recentVisitResponse;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;

public class RecentVisitResponsee {
    private Header header;
    private Data data;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
                "header=" + header +
                ", data=" + data +
                ", response=" + response +
                "]";
    }
}
