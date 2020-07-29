package com.cynoteck.petofyvet.response.getPetReportsResponse;

public class GetPetHospitalizationData {
    private PagingHeader pagingHeader;

    private PetHospitalizationsList[] petHospitalizationsList;

    public PagingHeader getPagingHeader ()
    {
        return pagingHeader;
    }

    public void setPagingHeader (PagingHeader pagingHeader)
    {
        this.pagingHeader = pagingHeader;
    }

    public PetHospitalizationsList[] getPetHospitalizationsList ()
    {
        return petHospitalizationsList;
    }

    public void setPetHospitalizationsList (PetHospitalizationsList[] petHospitalizationsList)
    {
        this.petHospitalizationsList = petHospitalizationsList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pagingHeader = "+pagingHeader+", petHospitalizationsList = "+petHospitalizationsList+"]";
    }
}