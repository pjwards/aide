package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pjwards.aide.domain.enums.Check;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Check presenceCheck;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonManagedReference
    private Conference conference;

    public Long getId() {
        return id;
    }

    public Check getPresenceCheck() {
        return presenceCheck;
    }

    public Presence setPresenceCheck(Check presenceCheck) {
        this.presenceCheck = presenceCheck;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Presence setUser(User user) {
        this.user = user;
        return this;
    }

    public Conference getConference() {
        return conference;
    }

    public Presence setConference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public Presence() {

    }

    public Presence(Check presenceCheck) {
        this.presenceCheck = presenceCheck;
    }

    public void update(Presence update) {
        this.presenceCheck = update.presenceCheck;
        this.user = update.user;
        this.conference = update.conference;
    }
}
