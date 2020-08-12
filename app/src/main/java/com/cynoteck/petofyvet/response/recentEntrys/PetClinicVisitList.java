package com.cynoteck.petofyvet.response.recentEntrys;

import com.cynoteck.petofyvet.response.getPetDetailsResponse.PetAgeList;
import com.cynoteck.petofyvet.response.getPetReportsResponse.NatureOfVisit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PetClinicVisitList {
   /* private String id;
    private String encryptedId;
    private String petId;
    private String petName;
    private String petUniqueId;
    private String petParentName;
    private String petAge;
    private String petSex;
    private String veterinarian;
    private String visitDate;
    private String natureOfVisitId;
    private String natureOfVisitList;
    private String vaccine;
    private String optionalVaccine;
    private String description;
    private String weightLbs;
    private String weightOz;
    private String temperature;
    private String dateOfOnset;
    private String dewormerName;
    private String treatmentRemarks;
    private String diagnosisProcedure;
    private String followUpId;
    private String followUpList;
    private String followUpDate;
    private String documents;
    private String createdOn;
    private String createdBy;
    private String updatedOn;
    private String updatedBy;
    private String veterinarianUserId;
    private NatureOfVisit natureOfVisit;
    private String pet;
    private CreatedByUser createdByUser;
    private UpdatedByUser updatedByUser;
    private String followUp;
    private List<PetAgeList> petAgeList = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetUniqueId() {
        return petUniqueId;
    }

    public void setPetUniqueId(String petUniqueId) {
        this.petUniqueId = petUniqueId;
    }

    public String getPetParentName() {
        return petParentName;
    }

    public void setPetParentName(String petParentName) {
        this.petParentName = petParentName;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(String veterinarian) {
        this.veterinarian = veterinarian;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getNatureOfVisitId() {
        return natureOfVisitId;
    }

    public void setNatureOfVisitId(String natureOfVisitId) {
        this.natureOfVisitId = natureOfVisitId;
    }

    public String getNatureOfVisitList() {
        return natureOfVisitList;
    }

    public void setNatureOfVisitList(String natureOfVisitList) {
        this.natureOfVisitList = natureOfVisitList;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getOptionalVaccine() {
        return optionalVaccine;
    }

    public void setOptionalVaccine(String optionalVaccine) {
        this.optionalVaccine = optionalVaccine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeightLbs() {
        return weightLbs;
    }

    public void setWeightLbs(String weightLbs) {
        this.weightLbs = weightLbs;
    }

    public String getWeightOz() {
        return weightOz;
    }

    public void setWeightOz(String weightOz) {
        this.weightOz = weightOz;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDateOfOnset() {
        return dateOfOnset;
    }

    public void setDateOfOnset(String dateOfOnset) {
        this.dateOfOnset = dateOfOnset;
    }

    public String getDewormerName() {
        return dewormerName;
    }

    public void setDewormerName(String dewormerName) {
        this.dewormerName = dewormerName;
    }

    public String getTreatmentRemarks() {
        return treatmentRemarks;
    }

    public void setTreatmentRemarks(String treatmentRemarks) {
        this.treatmentRemarks = treatmentRemarks;
    }

    public String getDiagnosisProcedure() {
        return diagnosisProcedure;
    }

    public void setDiagnosisProcedure(String diagnosisProcedure) {
        this.diagnosisProcedure = diagnosisProcedure;
    }

    public String getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(String followUpId) {
        this.followUpId = followUpId;
    }

    public String getFollowUpList() {
        return followUpList;
    }

    public void setFollowUpList(String followUpList) {
        this.followUpList = followUpList;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getVeterinarianUserId() {
        return veterinarianUserId;
    }

    public void setVeterinarianUserId(String veterinarianUserId) {
        this.veterinarianUserId = veterinarianUserId;
    }

    public NatureOfVisit getNatureOfVisit() {
        return natureOfVisit;
    }

    public void setNatureOfVisit(NatureOfVisit natureOfVisit) {
        this.natureOfVisit = natureOfVisit;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public CreatedByUser getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(CreatedByUser createdByUser) {
        this.createdByUser = createdByUser;
    }

    public UpdatedByUser getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(UpdatedByUser updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public List<PetAgeList> getPetAgeList() {
        return petAgeList;
    }

    public void setPetAgeList(List<PetAgeList> petAgeList) {
        this.petAgeList = petAgeList;
    }

    @Override
    public String toString() {
        return "ClassPojo [id=" + id +", encryptedId=" + encryptedId +", petId=" + petId +", petName=" + petName +", petUniqueId=" + petUniqueId +", petParentName=" + petParentName +", petAge=" + petAge +", petSex=" + petSex +", veterinarian='" + veterinarian +", visitDate=" + visitDate +", natureOfVisitId=" + natureOfVisitId +", natureOfVisitList=" + natureOfVisitList +", vaccine=" + vaccine +", optionalVaccine=" + optionalVaccine +", description='" + description  +", weightLbs='" + weightLbs +", weightOz=" + weightOz +", temperature='" + temperature +", dateOfOnset='" + dateOfOnset +", dewormerName=" + dewormerName +", treatmentRemarks='" + treatmentRemarks +", diagnosisProcedure='" + diagnosisProcedure +", followUpId=" + followUpId +", followUpList=" + followUpList +", followUpDate='" + followUpDate +", documents=" + documents +", createdOn='" + createdOn +", createdBy='" + createdBy +", updatedOn='" + updatedOn  +", updatedBy='" + updatedBy + ", veterinarianUserId=" + veterinarianUserId +", natureOfVisit=" + natureOfVisit +", pet=" + pet +", createdByUser=" + createdByUser +", updatedByUser=" + updatedByUser +", followUp=" + followUp +", petAgeList=" + petAgeList +"]";
    }*/

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("encryptedId")
    @Expose
    private String encryptedId;
    @SerializedName("petId")
    @Expose
    private Double petId;
    @SerializedName("petName")
    @Expose
    private String petName;
    @SerializedName("petUniqueId")
    @Expose
    private String petUniqueId;
    @SerializedName("petParentName")
    @Expose
    private String petParentName;
    @SerializedName("petAge")
    @Expose
    private String petAge;
    @SerializedName("petSex")
    @Expose
    private String petSex;
    @SerializedName("veterinarian")
    @Expose
    private String veterinarian;
    @SerializedName("visitDate")
    @Expose
    private String visitDate;
    @SerializedName("natureOfVisitId")
    @Expose
    private Double natureOfVisitId;
    @SerializedName("natureOfVisitList")
    @Expose
    private Object natureOfVisitList;
    @SerializedName("vaccine")
    @Expose
    private Object vaccine;
    @SerializedName("optionalVaccine")
    @Expose
    private Object optionalVaccine;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("weightLbs")
    @Expose
    private String weightLbs;
    @SerializedName("weightOz")
    @Expose
    private Object weightOz;
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("dateOfOnset")
    @Expose
    private String dateOfOnset;
    @SerializedName("dewormerName")
    @Expose
    private Object dewormerName;
    @SerializedName("treatmentRemarks")
    @Expose
    private String treatmentRemarks;
    @SerializedName("diagnosisProcedure")
    @Expose
    private String diagnosisProcedure;
    @SerializedName("followUpId")
    @Expose
    private Object followUpId;
    @SerializedName("followUpList")
    @Expose
    private Object followUpList;
    @SerializedName("followUpDate")
    @Expose
    private String followUpDate;
    @SerializedName("documents")
    @Expose
    private Object documents;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("veterinarianUserId")
    @Expose
    private Object veterinarianUserId;
    @SerializedName("natureOfVisit")
    @Expose
    private NatureOfVisitsRequest natureOfVisit;
    @SerializedName("pet")
    @Expose
    private Object pet;
    @SerializedName("createdByUser")
    @Expose
    private CreatedByUser createdByUser;
    @SerializedName("updatedByUser")
    @Expose
    private UpdatedByUser updatedByUser;
    @SerializedName("followUp")
    @Expose
    private Object followUp;
    @SerializedName("petAgeList")
    @Expose
    private List<PetAgeListRequset> petAgeList = null;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public Double getPetId() {
        return petId;
    }

    public void setPetId(Double petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetUniqueId() {
        return petUniqueId;
    }

    public void setPetUniqueId(String petUniqueId) {
        this.petUniqueId = petUniqueId;
    }

    public String getPetParentName() {
        return petParentName;
    }

    public void setPetParentName(String petParentName) {
        this.petParentName = petParentName;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(String veterinarian) {
        this.veterinarian = veterinarian;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public Double getNatureOfVisitId() {
        return natureOfVisitId;
    }

    public void setNatureOfVisitId(Double natureOfVisitId) {
        this.natureOfVisitId = natureOfVisitId;
    }

    public Object getNatureOfVisitList() {
        return natureOfVisitList;
    }

    public void setNatureOfVisitList(Object natureOfVisitList) {
        this.natureOfVisitList = natureOfVisitList;
    }

    public Object getVaccine() {
        return vaccine;
    }

    public void setVaccine(Object vaccine) {
        this.vaccine = vaccine;
    }

    public Object getOptionalVaccine() {
        return optionalVaccine;
    }

    public void setOptionalVaccine(Object optionalVaccine) {
        this.optionalVaccine = optionalVaccine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeightLbs() {
        return weightLbs;
    }

    public void setWeightLbs(String weightLbs) {
        this.weightLbs = weightLbs;
    }

    public Object getWeightOz() {
        return weightOz;
    }

    public void setWeightOz(Object weightOz) {
        this.weightOz = weightOz;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDateOfOnset() {
        return dateOfOnset;
    }

    public void setDateOfOnset(String dateOfOnset) {
        this.dateOfOnset = dateOfOnset;
    }

    public Object getDewormerName() {
        return dewormerName;
    }

    public void setDewormerName(Object dewormerName) {
        this.dewormerName = dewormerName;
    }

    public String getTreatmentRemarks() {
        return treatmentRemarks;
    }

    public void setTreatmentRemarks(String treatmentRemarks) {
        this.treatmentRemarks = treatmentRemarks;
    }

    public String getDiagnosisProcedure() {
        return diagnosisProcedure;
    }

    public void setDiagnosisProcedure(String diagnosisProcedure) {
        this.diagnosisProcedure = diagnosisProcedure;
    }

    public Object getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(Object followUpId) {
        this.followUpId = followUpId;
    }

    public Object getFollowUpList() {
        return followUpList;
    }

    public void setFollowUpList(Object followUpList) {
        this.followUpList = followUpList;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public Object getDocuments() {
        return documents;
    }

    public void setDocuments(Object documents) {
        this.documents = documents;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getVeterinarianUserId() {
        return veterinarianUserId;
    }

    public void setVeterinarianUserId(Object veterinarianUserId) {
        this.veterinarianUserId = veterinarianUserId;
    }

    public NatureOfVisitsRequest getNatureOfVisit() {
        return natureOfVisit;
    }

    public void setNatureOfVisit(NatureOfVisitsRequest natureOfVisit) {
        this.natureOfVisit = natureOfVisit;
    }

    public Object getPet() {
        return pet;
    }

    public void setPet(Object pet) {
        this.pet = pet;
    }

    public CreatedByUser getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(CreatedByUser createdByUser) {
        this.createdByUser = createdByUser;
    }

    public UpdatedByUser getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(UpdatedByUser updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public Object getFollowUp() {
        return followUp;
    }

    public void setFollowUp(Object followUp) {
        this.followUp = followUp;
    }

    public List<PetAgeListRequset> getPetAgeList() {
        return petAgeList;
    }

    public void setPetAgeList(List<PetAgeListRequset> petAgeList) {
        this.petAgeList = petAgeList;
    }
}
