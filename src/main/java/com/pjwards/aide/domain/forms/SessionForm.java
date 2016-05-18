package com.pjwards.aide.domain.forms;

import com.pjwards.aide.domain.Session;
import com.pjwards.aide.domain.User;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.stream.Collectors;

public class SessionForm {

    @NotEmpty
    private String title = "";

    @NotEmpty
    private String description = "";

    private String slideUrl = "";

    private String slideEmbed = "";

    private String videoUrl = "";

    private String videoEmbed = "";

    private Long programId;

    private Long roomId;

    private List<Long> speakers;

    public SessionForm() {
    }

    public SessionForm(Session session) {
        this.title = session.getTitle();
        this.description = session.getDescription();
        this.slideUrl = session.getSlideUrl() != null ? session.getSlideUrl() : "";
        this.slideEmbed = session.getSlideEmbed() != null ? session.getSlideEmbed() : "";
        this.videoUrl = session.getVideoUrl() != null ? session.getVideoUrl() : "";
        this.videoEmbed = session.getVideoEmbed() != null ? session.getVideoEmbed() : "";
        this.programId = session.getProgram() != null ? session.getProgram().getId() : null;
        this.roomId = session.getRoom() != null ? session.getRoom().getId() : null;
        this.speakers = session.getSpeakerSet().stream().map(User::getId).collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlideUrl() {
        return slideUrl;
    }

    public void setSlideUrl(String slideUrl) {
        this.slideUrl = slideUrl;
    }

    public String getSlideEmbed() {
        return slideEmbed;
    }

    public void setSlideEmbed(String slideEmbed) {
        this.slideEmbed = slideEmbed;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoEmbed() {
        return videoEmbed;
    }

    public void setVideoEmbed(String videoEmbed) {
        this.videoEmbed = videoEmbed;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<Long> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Long> speakers) {
        this.speakers = speakers;
    }
}
