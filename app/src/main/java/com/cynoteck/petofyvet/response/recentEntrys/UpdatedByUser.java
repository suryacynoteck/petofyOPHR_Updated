package com.cynoteck.petofyvet.response.recentEntrys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatedByUser {
   /* private String encryptedId;
    private String userId;
    private String email;
    private String password;
    private String customerPassword;
    private String petParentPassword;
    private String customerEmail;
    private String phoneNumber;
    private String guestEmail;
    private String forgotPasswordEmail;
    private String rememberMe;
    private String isLogin;
    private String firstName;
    private String lastName;
    private String confirmPassword;
    private String petParentConfirmPassword;
    private String isPrivacyPolicyCheck;
    private String notificationStatus;
    private String address;
    private String locationId;
    private String cityId;
    private String latLong;
    private String zipCode;
    private String profileImageUrl;
    private String locationList;
    private String roleId;
    private String status;
    private String areYouProvider;
    private String providerFirstName;
    private String providerLastName;
    private String providerPhoneNumber;
    private String providerEmail;
    private String providerPassword;
    private String providerConfirmPassword;
    private String isMobileNumberVerified;
    private String isEmailVerified;
    private String isActive;
    private String fullName;
    private String userRole;

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getPetParentPassword() {
        return petParentPassword;
    }

    public void setPetParentPassword(String petParentPassword) {
        this.petParentPassword = petParentPassword;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getForgotPasswordEmail() {
        return forgotPasswordEmail;
    }

    public void setForgotPasswordEmail(String forgotPasswordEmail) {
        this.forgotPasswordEmail = forgotPasswordEmail;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPetParentConfirmPassword() {
        return petParentConfirmPassword;
    }

    public void setPetParentConfirmPassword(String petParentConfirmPassword) {
        this.petParentConfirmPassword = petParentConfirmPassword;
    }

    public String getIsPrivacyPolicyCheck() {
        return isPrivacyPolicyCheck;
    }

    public void setIsPrivacyPolicyCheck(String isPrivacyPolicyCheck) {
        this.isPrivacyPolicyCheck = isPrivacyPolicyCheck;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getLocationList() {
        return locationList;
    }

    public void setLocationList(String locationList) {
        this.locationList = locationList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAreYouProvider() {
        return areYouProvider;
    }

    public void setAreYouProvider(String areYouProvider) {
        this.areYouProvider = areYouProvider;
    }

    public String getProviderFirstName() {
        return providerFirstName;
    }

    public void setProviderFirstName(String providerFirstName) {
        this.providerFirstName = providerFirstName;
    }

    public String getProviderLastName() {
        return providerLastName;
    }

    public void setProviderLastName(String providerLastName) {
        this.providerLastName = providerLastName;
    }

    public String getProviderPhoneNumber() {
        return providerPhoneNumber;
    }

    public void setProviderPhoneNumber(String providerPhoneNumber) {
        this.providerPhoneNumber = providerPhoneNumber;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderPassword() {
        return providerPassword;
    }

    public void setProviderPassword(String providerPassword) {
        this.providerPassword = providerPassword;
    }

    public String getProviderConfirmPassword() {
        return providerConfirmPassword;
    }

    public void setProviderConfirmPassword(String providerConfirmPassword) {
        this.providerConfirmPassword = providerConfirmPassword;
    }

    public String getIsMobileNumberVerified() {
        return isMobileNumberVerified;
    }

    public void setIsMobileNumberVerified(String isMobileNumberVerified) {
        this.isMobileNumberVerified = isMobileNumberVerified;
    }

    public String getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "ClassPojo [" +
                "encryptedId=" + encryptedId +
                ", userId='" + userId + '\'' +
                ", email=" + email +
                ", password=" + password +
                ", customerPassword=" + customerPassword +
                ", petParentPassword=" + petParentPassword +
                ", customerEmail=" + customerEmail +
                ", phoneNumber=" + phoneNumber +
                ", guestEmail=" + guestEmail +
                ", forgotPasswordEmail=" + forgotPasswordEmail +
                ", rememberMe=" + rememberMe +
                ", isLogin=" + isLogin +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", confirmPassword=" + confirmPassword +
                ", petParentConfirmPassword=" + petParentConfirmPassword +
                ", isPrivacyPolicyCheck=" + isPrivacyPolicyCheck +
                ", notificationStatus=" + notificationStatus +
                ", address=" + address +
                ", locationId=" + locationId +
                ", cityId=" + cityId +
                ", latLong=" + latLong +
                ", zipCode=" + zipCode +
                ", profileImageUrl=" + profileImageUrl +
                ", locationList=" + locationList +
                ", roleId='" + roleId + '\'' +
                ", status=" + status +
                ", areYouProvider=" + areYouProvider +
                ", providerFirstName=" + providerFirstName +
                ", providerLastName=" + providerLastName +
                ", providerPhoneNumber=" + providerPhoneNumber +
                ", providerEmail=" + providerEmail +
                ", providerPassword=" + providerPassword +
                ", providerConfirmPassword=" + providerConfirmPassword +
                ", isMobileNumberVerified=" + isMobileNumberVerified +
                ", isEmailVerified=" + isEmailVerified +
                ", isActive=" + isActive +
                ", fullName=" + fullName +
                ", userRole=" + userRole +
                "]";
    }*/
   @SerializedName("encryptedId")
   @Expose
   private Object encryptedId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("customerPassword")
    @Expose
    private Object customerPassword;
    @SerializedName("petParentPassword")
    @Expose
    private Object petParentPassword;
    @SerializedName("customerEmail")
    @Expose
    private Object customerEmail;
    @SerializedName("phoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("guestEmail")
    @Expose
    private Object guestEmail;
    @SerializedName("forgotPasswordEmail")
    @Expose
    private Object forgotPasswordEmail;
    @SerializedName("rememberMe")
    @Expose
    private Boolean rememberMe;
    @SerializedName("isLogin")
    @Expose
    private Boolean isLogin;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("confirmPassword")
    @Expose
    private Object confirmPassword;
    @SerializedName("petParentConfirmPassword")
    @Expose
    private Object petParentConfirmPassword;
    @SerializedName("isPrivacyPolicyCheck")
    @Expose
    private Boolean isPrivacyPolicyCheck;
    @SerializedName("notificationStatus")
    @Expose
    private Boolean notificationStatus;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("latLong")
    @Expose
    private Object latLong;
    @SerializedName("zipCode")
    @Expose
    private Object zipCode;
    @SerializedName("profileImageUrl")
    @Expose
    private Object profileImageUrl;
    @SerializedName("locationList")
    @Expose
    private Object locationList;
    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("areYouProvider")
    @Expose
    private Boolean areYouProvider;
    @SerializedName("providerFirstName")
    @Expose
    private Object providerFirstName;
    @SerializedName("providerLastName")
    @Expose
    private Object providerLastName;
    @SerializedName("providerPhoneNumber")
    @Expose
    private Object providerPhoneNumber;
    @SerializedName("providerEmail")
    @Expose
    private Object providerEmail;
    @SerializedName("providerPassword")
    @Expose
    private Object providerPassword;
    @SerializedName("providerConfirmPassword")
    @Expose
    private Object providerConfirmPassword;
    @SerializedName("isMobileNumberVerified")
    @Expose
    private Boolean isMobileNumberVerified;
    @SerializedName("isEmailVerified")
    @Expose
    private Boolean isEmailVerified;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("fullName")
    @Expose
    private Object fullName;
    @SerializedName("userRole")
    @Expose
    private Object userRole;

    public Object getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(Object encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(Object customerPassword) {
        this.customerPassword = customerPassword;
    }

    public Object getPetParentPassword() {
        return petParentPassword;
    }

    public void setPetParentPassword(Object petParentPassword) {
        this.petParentPassword = petParentPassword;
    }

    public Object getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(Object customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(Object guestEmail) {
        this.guestEmail = guestEmail;
    }

    public Object getForgotPasswordEmail() {
        return forgotPasswordEmail;
    }

    public void setForgotPasswordEmail(Object forgotPasswordEmail) {
        this.forgotPasswordEmail = forgotPasswordEmail;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(Object confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Object getPetParentConfirmPassword() {
        return petParentConfirmPassword;
    }

    public void setPetParentConfirmPassword(Object petParentConfirmPassword) {
        this.petParentConfirmPassword = petParentConfirmPassword;
    }

    public Boolean getIsPrivacyPolicyCheck() {
        return isPrivacyPolicyCheck;
    }

    public void setIsPrivacyPolicyCheck(Boolean isPrivacyPolicyCheck) {
        this.isPrivacyPolicyCheck = isPrivacyPolicyCheck;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Object getLatLong() {
        return latLong;
    }

    public void setLatLong(Object latLong) {
        this.latLong = latLong;
    }

    public Object getZipCode() {
        return zipCode;
    }

    public void setZipCode(Object zipCode) {
        this.zipCode = zipCode;
    }

    public Object getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(Object profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Object getLocationList() {
        return locationList;
    }

    public void setLocationList(Object locationList) {
        this.locationList = locationList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getAreYouProvider() {
        return areYouProvider;
    }

    public void setAreYouProvider(Boolean areYouProvider) {
        this.areYouProvider = areYouProvider;
    }

    public Object getProviderFirstName() {
        return providerFirstName;
    }

    public void setProviderFirstName(Object providerFirstName) {
        this.providerFirstName = providerFirstName;
    }

    public Object getProviderLastName() {
        return providerLastName;
    }

    public void setProviderLastName(Object providerLastName) {
        this.providerLastName = providerLastName;
    }

    public Object getProviderPhoneNumber() {
        return providerPhoneNumber;
    }

    public void setProviderPhoneNumber(Object providerPhoneNumber) {
        this.providerPhoneNumber = providerPhoneNumber;
    }

    public Object getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(Object providerEmail) {
        this.providerEmail = providerEmail;
    }

    public Object getProviderPassword() {
        return providerPassword;
    }

    public void setProviderPassword(Object providerPassword) {
        this.providerPassword = providerPassword;
    }

    public Object getProviderConfirmPassword() {
        return providerConfirmPassword;
    }

    public void setProviderConfirmPassword(Object providerConfirmPassword) {
        this.providerConfirmPassword = providerConfirmPassword;
    }

    public Boolean getIsMobileNumberVerified() {
        return isMobileNumberVerified;
    }

    public void setIsMobileNumberVerified(Boolean isMobileNumberVerified) {
        this.isMobileNumberVerified = isMobileNumberVerified;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getFullName() {
        return fullName;
    }

    public void setFullName(Object fullName) {
        this.fullName = fullName;
    }

    public Object getUserRole() {
        return userRole;
    }

    public void setUserRole(Object userRole) {
        this.userRole = userRole;
    }
}
