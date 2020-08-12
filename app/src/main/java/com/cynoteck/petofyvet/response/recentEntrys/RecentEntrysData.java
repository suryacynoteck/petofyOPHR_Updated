package com.cynoteck.petofyvet.response.recentEntrys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentEntrysData {
    @SerializedName("petClinicVisitList")
    @Expose
    private List<PetClinicVisitList> petClinicVisitList = null;
    private String pagingHeader;

    public List<PetClinicVisitList> getPetClinicVisitList() {
        return petClinicVisitList;
    }

    public void setPetClinicVisitList(List<PetClinicVisitList> petClinicVisitList) {
        this.petClinicVisitList = petClinicVisitList;
    }

    public String getPagingHeader() {
        return pagingHeader;
    }

    public void setPagingHeader(String pagingHeader) {
        this.pagingHeader = pagingHeader;
    }

    @Override
    public String toString() {
        return "ClassPojo [" +
                "petClinicVisitList=" + petClinicVisitList +
                ", pagingHeader=" + pagingHeader +
                "]";
    }
}
