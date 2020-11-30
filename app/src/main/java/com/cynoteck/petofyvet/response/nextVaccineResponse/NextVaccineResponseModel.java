package com.cynoteck.petofyvet.response.nextVaccineResponse;

public class NextVaccineResponseModel {
    private String nextVaccinationDate;
    private String nextDate;
    private String vaccineName;
    private String vaccineType;
    private String vaccinationStatusMessage;
    private Boolean isVaccinated;

    public String getNextVaccinationDate() {
        return nextVaccinationDate;
    }

    public void setNextVaccinationDate(String nextVaccinationDate) {
        this.nextVaccinationDate = nextVaccinationDate;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public String getVaccinationStatusMessage() {
        return vaccinationStatusMessage;
    }

    public void setVaccinationStatusMessage(String vaccinationStatusMessage) {
        this.vaccinationStatusMessage = vaccinationStatusMessage;
    }

    public Boolean getIsVaccinated() {
        return isVaccinated;
    }

    public void setIsVaccinated(Boolean isVaccinated) {
        this.isVaccinated = isVaccinated;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "nextVaccinationDate=" + nextVaccinationDate +
                ", nextDate=" + nextDate +
                ", vaccineName=" + vaccineName +
                ", vaccineType=" + vaccineType +
                ", vaccinationStatusMessage=" + vaccinationStatusMessage +
                ", isVaccinated=" + isVaccinated +
                "]";
    }
}
