package com.cynoteck.petofyvet.response.recentEntrys;

import com.cynoteck.petofyvet.response.Header;
import com.cynoteck.petofyvet.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentEntrysResponse {
   /* private Header header;
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
        return "ClassPojo[ header=" + header +", data=" + data +", response=" + response +"]";
    }*/
   @SerializedName("header")
   @Expose
   private RecentEntrysHeder header;
    @SerializedName("data")
    @Expose
    private RecentEntrysData data;
    @SerializedName("response")
    @Expose
    private EntrysResponse response;

    public RecentEntrysHeder getHeader() {
        return header;
    }

    public void setHeader(RecentEntrysHeder header) {
        this.header = header;
    }

    public RecentEntrysData getData() {
        return data;
    }

    public void setData(RecentEntrysData data) {
        this.data = data;
    }

    public EntrysResponse getResponse() {
        return response;
    }

    public void setResponse(EntrysResponse response) {
        this.response = response;
    }
}
