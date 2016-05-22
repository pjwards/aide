package com.pjwards.aide.domain;

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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_presence", joinColumns = { @JoinColumn(name = "presence_id_frk") },
            inverseJoinColumns = { @JoinColumn(name = "user_id_frk") })
    private Set<User> userSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "conference_presence", joinColumns = { @JoinColumn(name = "presence_id_frk") },
            inverseJoinColumns = { @JoinColumn(name = "conference_id_frk") })
    private Set<Conference> conferenceSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Check getPresenceCheck() {
        return presenceCheck;
    }

    public void setPresenceCheck(Check presenceCheck) {
        this.presenceCheck = presenceCheck;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Set<Conference> getConferenceSet() {
        return conferenceSet;
    }

    public void setConferenceSet(Set<Conference> conferenceSet) {
        this.conferenceSet = conferenceSet;
    }

    public Presence(){

    }

    public Presence(Check presenceCheck){
        this.presenceCheck = presenceCheck;
    }

    public void update(Presence update){
        this.presenceCheck = update.presenceCheck;
        this.userSet = update.userSet;
        this.conferenceSet = update.conferenceSet;
    }
}
