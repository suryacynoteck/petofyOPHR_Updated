package com.cynoteck.petofyvet.response.immunizationVaccineType;

import java.util.List;

public class ImmunizationTypeModel {
  
    private List<String> primaryVaccine = null;
    private String nextVisitDate;
    private List<VaccineTypeList> vaccineTypeList = null;
    private String age;

    public List<String> getPrimaryVaccine() {
        return primaryVaccine;
    }

    public void setPrimaryVaccine(List<String> primaryVaccine) {
        this.primaryVaccine = primaryVaccine;
    }

    public String getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(String nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

    public List<VaccineTypeList> getVaccineTypeList() {
        return vaccineTypeList;
    }

    public void setVaccineTypeList(List<VaccineTypeList> vaccineTypeList) {
        this.vaccineTypeList = vaccineTypeList;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ClassPojo [primaryVaccine = "+primaryVaccine+", nextVisitDate= "+nextVisitDate+", vaccineTypeList= "+vaccineTypeList+", age= "+age+"]";

    }

}
