package com.cynoteck.petofyOPHR.params.appointmentParams;

public class CreateAppointParams {
    private String petId;

    private String duration;

    private String description;

    private String eventStartTime;

    private String title;

    private String userId;

    private String eventStartDate;

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getEventStartTime ()
    {
        return eventStartTime;
    }

    public void setEventStartTime (String eventStartTime)
    {
        this.eventStartTime = eventStartTime;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getEventStartDate ()
    {
        return eventStartDate;
    }

    public void setEventStartDate (String eventStartDate)
    {
        this.eventStartDate = eventStartDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petId = "+petId+", duration = "+duration+", description = "+description+", eventStartTime = "+eventStartTime+", title = "+title+", userId = "+userId+", eventStartDate = "+eventStartDate+"]";
    }
}


