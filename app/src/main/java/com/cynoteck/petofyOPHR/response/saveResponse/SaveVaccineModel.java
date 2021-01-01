package com.cynoteck.petofyOPHR.response.saveResponse;

public class SaveVaccineModel {
    
    private String id;
    private String vaccineType;
    private String brandName;
    private String vaccine;
    private String vaccineDose;
    private String immunizationDate;
    private String isTemp;
    private String statusCode;
    private String errorCode;
    private String errorMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getVaccineDose() {
        return vaccineDose;
    }

    public void setVaccineDose(String vaccineDose) {
        this.vaccineDose = vaccineDose;
    }

    public String getImmunizationDate() {
        return immunizationDate;
    }

    public void setImmunizationDate(String immunizationDate) {
        this.immunizationDate = immunizationDate;
    }

    public String getIsTemp() {
        return isTemp;
    }

    public void setIsTemp(String isTemp) {
        this.isTemp = isTemp;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "id=" + id +
                ", vaccineType=" + vaccineType +
                ", brandName=" + brandName +
                ", vaccine=" + vaccine +
                ", vaccineDose=" + vaccineDose +
                ", immunizationDate=" + immunizationDate +
                ", isTemp=" + isTemp +
                ", statusCode=" + statusCode +
                ", errorCode=" + errorCode +
                ", errorMessage=" + errorMessage +
                "]";
    }
}
