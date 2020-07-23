package com.cynoteck.petofyvet.response.updateProfileResponse;

public class PetServiceModel {
    private Integer id;
    private String serviceType1;
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceType1() {
        return serviceType1;
    }

    public void setServiceType1(String serviceType1) {
        this.serviceType1 = serviceType1;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", serviceType1 = "+serviceType1+", isActive = "+isActive+"]";
    }
}
