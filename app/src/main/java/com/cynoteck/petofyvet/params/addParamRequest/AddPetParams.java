package com.cynoteck.petofyvet.params.addParamRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPetParams {

    private RequestBody petCategoryId;
    private RequestBody petSexId;
    private RequestBody petAgeId;
    private RequestBody petSizeId;
    private RequestBody petColorId;
    private RequestBody petBreedId;
    private RequestBody petName;
    private RequestBody petParentName;
    private RequestBody contactNumber;
    private RequestBody address;
    private RequestBody dateOfBirth;
    private RequestBody description;
    private RequestBody createDate;
    private MultipartBody.Part petProfileImageUrl=null;
    private MultipartBody.Part firstServiceImageUrl=null;
    private MultipartBody.Part secondServiceImageUrl=null;
    private MultipartBody.Part thirdServiceImageUrl=null;
    private MultipartBody.Part fourthServiceImageUrl=null;
    private MultipartBody.Part fifthServiceImageUrl=null;


    public RequestBody getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(RequestBody petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public RequestBody getPetSexId() {
        return petSexId;
    }

    public void setPetSexId(RequestBody petSexId) {
        this.petSexId = petSexId;
    }

    public RequestBody getPetAgeId() {
        return petAgeId;
    }

    public void setPetAgeId(RequestBody petAgeId) {
        this.petAgeId = petAgeId;
    }

    public RequestBody getPetSizeId() {
        return petSizeId;
    }

    public void setPetSizeId(RequestBody petSizeId) {
        this.petSizeId = petSizeId;
    }

    public RequestBody getPetColorId() {
        return petColorId;
    }

    public void setPetColorId(RequestBody petColorId) {
        this.petColorId = petColorId;
    }

    public RequestBody getPetBreedId() {
        return petBreedId;
    }

    public void setPetBreedId(RequestBody petBreedId) {
        this.petBreedId = petBreedId;
    }

    public RequestBody getPetName() {
        return petName;
    }

    public void setPetName(RequestBody petName) {
        this.petName = petName;
    }

    public RequestBody getPetParentName() {
        return petParentName;
    }

    public void setPetParentName(RequestBody petParentName) {
        this.petParentName = petParentName;
    }

    public RequestBody getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(RequestBody contactNumber) {
        this.contactNumber = contactNumber;
    }

    public RequestBody getAddress() {
        return address;
    }

    public void setAddress(RequestBody address) {
        this.address = address;
    }

    public RequestBody getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(RequestBody dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public RequestBody getDescription() {
        return description;
    }

    public void setDescription(RequestBody description) {
        this.description = description;
    }

    public RequestBody getCreateDate() {
        return createDate;
    }

    public void setCreateDate(RequestBody createDate) {
        this.createDate = createDate;
    }

    public MultipartBody.Part getPetProfileImageUrl() {
        return petProfileImageUrl;
    }

    public void setPetProfileImageUrl(MultipartBody.Part petProfileImageUrl) {
        this.petProfileImageUrl = petProfileImageUrl;
    }

    public MultipartBody.Part getFirstServiceImageUrl() {
        return firstServiceImageUrl;
    }

    public void setFirstServiceImageUrl(MultipartBody.Part firstServiceImageUrl) {
        this.firstServiceImageUrl = firstServiceImageUrl;
    }

    public MultipartBody.Part getSecondServiceImageUrl() {
        return secondServiceImageUrl;
    }

    public void setSecondServiceImageUrl(MultipartBody.Part secondServiceImageUrl) {
        this.secondServiceImageUrl = secondServiceImageUrl;
    }

    public MultipartBody.Part getThirdServiceImageUrl() {
        return thirdServiceImageUrl;
    }

    public void setThirdServiceImageUrl(MultipartBody.Part thirdServiceImageUrl) {
        this.thirdServiceImageUrl = thirdServiceImageUrl;
    }

    public MultipartBody.Part getFourthServiceImageUrl() {
        return fourthServiceImageUrl;
    }

    public void setFourthServiceImageUrl(MultipartBody.Part fourthServiceImageUrl) {
        this.fourthServiceImageUrl = fourthServiceImageUrl;
    }

    public MultipartBody.Part getFifthServiceImageUrl() {
        return fifthServiceImageUrl;
    }

    public void setFifthServiceImageUrl(MultipartBody.Part fifthServiceImageUrl) {
        this.fifthServiceImageUrl = fifthServiceImageUrl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petCategoryId = "+petCategoryId+", petSexId = "+petSexId+", petAgeId = "+petAgeId+", petSizeId = "+petSizeId+", petColorId= "+petColorId+", petName = "+petName+", petParentName = "+petParentName+", contactNumber = "+contactNumber+", dateOfBirth = "+dateOfBirth+", description = "+description+", createDate = "+createDate+", petProfileImageUrl = "+petProfileImageUrl+", firstServiceImageUrl = "+firstServiceImageUrl+", secondServiceImageUrl = "+secondServiceImageUrl+", thirdServiceImageUrl = "+thirdServiceImageUrl+", fourthServiceImageUrl = "+fourthServiceImageUrl+", fifthServiceImageUrl = "+fifthServiceImageUrl+"]";
    }
}
