package com.cynoteck.petofyvet.response.getPetReportsResponse;

public class GetPetClinicVisitListData {
    private PagingHeader pagingHeader;

    private PetClinicVisitList[] petClinicVisitList;

    public PagingHeader getPagingHeader ()
    {
        return pagingHeader;
    }

    public void setPagingHeader (PagingHeader pagingHeader)
    {
        this.pagingHeader = pagingHeader;
    }

    public PetClinicVisitList[] getPetClinicVisitList ()
    {
        return petClinicVisitList;
    }

    public void setPetClinicVisitList (PetClinicVisitList[] petClinicVisitList)
    {
        this.petClinicVisitList = petClinicVisitList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pagingHeader = "+pagingHeader+", petClinicVisitList = "+petClinicVisitList+"]";
    }
}