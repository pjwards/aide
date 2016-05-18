package com.pjwards.aide.domain.forms;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.ProgramType;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.stream.Collectors;

public class ProgramForm {

    @NotEmpty
    private String title = "";

    @NotEmpty
    private String description = "";

    @NotEmpty
    private String begin = "";

    @NotEmpty
    private String end = "";

    private ProgramType programType = ProgramType.SESSION;

    private String slideUrl = "";

    private String slideEmbed = "";

    private String videoUrl = "";

    private String videoEmbed = "";

    private Long programDateId;

    private Long roomId;

    private List<Long> speakers;

    public ProgramForm() {
    }

    public ProgramForm(Program program) {
        this.title = program.getTitle();
        this.description = program.getDescription();
        this.begin = program.getBegin();
        this.end = program.getEnd();
        this.programType = program.getProgramType();
        this.slideUrl = program.getSlideUrl() != null ? program.getSlideUrl() : "";
        this.slideEmbed = program.getSlideEmbed() != null ? program.getSlideEmbed() : "";
        this.videoUrl = program.getVideoUrl() != null ? program.getVideoUrl() : "";
        this.videoEmbed = program.getVideoEmbed() != null ? program.getVideoEmbed() : "";
        this.programDateId = program.getDate() != null ? program.getDate().getId() : null;
        this.roomId = program.getRoom() != null ? program.getRoom().getId() : null;
        this.speakers = program.getSpeakerSet().stream().map(User::getId).collect(Collectors.toList());
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

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public ProgramType getProgramType() {
        return programType;
    }

    public void setProgramType(ProgramType programType) {
        this.programType = programType;
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

    public Long getProgramDateId() {
        return programDateId;
    }

    public void setProgramDateId(Long programDateId) {
        this.programDateId = programDateId;
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

    public ProgramType[] getProgramTypeList() {
        return ProgramType.values();
    }
}
