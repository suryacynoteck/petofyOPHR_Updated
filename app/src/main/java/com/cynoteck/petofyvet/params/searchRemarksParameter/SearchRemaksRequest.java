package com.cynoteck.petofyvet.params.searchRemarksParameter;

import com.cynoteck.petofyvet.params.updateClinicVisitsParams.UpdateClinicReportsParams;

public class SearchRemaksRequest {

    private SearchRemaksParametr data;

    public SearchRemaksParametr getAddPetParams() {
        return data;
    }

    public void setAddPetParams(SearchRemaksParametr data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
