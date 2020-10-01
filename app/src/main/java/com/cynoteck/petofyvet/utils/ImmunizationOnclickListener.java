package com.cynoteck.petofyvet.utils;

import com.cynoteck.petofyvet.response.appointmentResponse.AppointmentList;

import java.util.ArrayList;

public interface ImmunizationOnclickListener {
    public void onItemClick(int position);
    public void onItemClickImmunizationPrimary(int position);
    public void onItemClickImmunizationBoosterOne(int position);
    public void onItemClickImmunizationBoosterTwo(int position);
}
