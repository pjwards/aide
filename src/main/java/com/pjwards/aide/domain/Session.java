package com.pjwards.aide.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Session {

    private static final Logger LOGGER = LoggerFactory.getLogger(Session.class);
    public static final int MAX_LENGTH_TITLE = 100;
    public static final int MAX_LENGTH_SLIDE_URL = 255;
    public static final int MAX_LENGTH_VIDEO_URL = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_TITLE)
    private String title;

    @Lob()
    @Column(nullable = false)
    private String description;

    @Column(length = MAX_LENGTH_SLIDE_URL)
    private String slideUrl;

    @Column(length = MAX_LENGTH_VIDEO_URL)
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SPEAKER_SESSION",
            joinColumns = @JoinColumn(name = "SESSION_ID_FRK"),
            inverseJoinColumns = @JoinColumn(name = "SPEAKER_ID_FRK")
    )
    private Set<User> speakerSet;

    public Session() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Session setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Session setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSlideUrl() {
        return slideUrl;
    }

    public Session setSlideUrl(String slideUrl) {
        this.slideUrl = slideUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Session setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Program getProgram() {
        return program;
    }

    public Session setProgram(Program program) {
        this.program = program;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Session setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Set<User> getSpeakerSet() {
        return speakerSet;
    }

    public Session setSpeakerSet(Set<User> speakerSet) {
        this.speakerSet = speakerSet;
        return this;
    }

    public void update(Session updated) {
        this.title = updated.title;
        this.description = updated.description;
    }

    public static class Builder {
        private Session built;

        public Builder(String title, String description) {
            built = new Session();
            built.title = title;
            built.description = description;
        }

        public Session build() {
            return built;
        }

        public Builder program(Program program) {
            built.program = program;
            return this;
        }

        public Builder room(Room room) {
            built.room = room;
            return this;
        }

        public Builder speakerSet(Set<User> speakerSet) {
            built.speakerSet = speakerSet;
            return this;
        }

        public Builder slideUrl(String slideUrl) {
            built.slideUrl = slideUrl;
            return this;
        }

        public Builder videoUrl(String videoUrl) {
            built.videoUrl = videoUrl;
            return this;
        }
    }

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }*/
}
