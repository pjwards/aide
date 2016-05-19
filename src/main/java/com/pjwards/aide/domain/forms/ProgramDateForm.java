package com.pjwards.aide.domain.forms;

import com.pjwards.aide.domain.ProgramDate;
import org.hibernate.validator.constraints.NotEmpty;

public class ProgramDateForm {

    @NotEmpty
    private String name = "";

    @NotEmpty
    private String day = "";

    private Long conferenceId;

    public ProgramDateForm() {
    }

    public ProgramDateForm(ProgramDate programDate) {
        this.name = programDate.getName();
        this.day = programDate.getFormattedDay();
        this.conferenceId = programDate.getConference().getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }
}
