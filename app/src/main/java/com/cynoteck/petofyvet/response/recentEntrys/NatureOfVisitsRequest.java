package com.cynoteck.petofyvet.response.recentEntrys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NatureOfVisitsRequest {
    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("nature")
    @Expose
    private String nature;
    @SerializedName("petClinicVisit")
    @Expose
    private List<Object> petClinicVisit = null;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public List<Object> getPetClinicVisit() {
        return petClinicVisit;
    }

    public void setPetClinicVisit(List<Object> petClinicVisit) {
        this.petClinicVisit = petClinicVisit;
    }
}
