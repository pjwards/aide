package com.pjwards.aide.domain.forms;

import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.User;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.stream.Collectors;

public class RoomForm {

    @NotEmpty
    private String name = "";

    @NotEmpty
    private String location = "";

    @NotEmpty
    private String description = "";

    private List<Long> managers;

    private Long conferenceId;

    public RoomForm() {
    }

    public RoomForm(Room room) {
        this.name = room.getName();
        this.location = room.getLocation();
        this.description = room.getDescription();
        this.conferenceId = room.getConference().getId();

        this.managers = room.getManagerSet().stream().map(User::getId).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getManagers() {
        return managers;
    }

    public void setManagers(List<Long> managers) {
        this.managers = managers;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }
}
