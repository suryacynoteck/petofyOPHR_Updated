package com.cynoteck.petofyvet.utils;

public interface OperatingHoursClickListener {
    public void onViewSetTime(int position, String id,String DayId, String startTime, String endTime, String isClose, String allDayOpen, String timeType);
}
