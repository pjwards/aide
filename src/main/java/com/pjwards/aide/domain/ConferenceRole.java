package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pjwards.aide.domain.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ConferenceRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role conferenceRole;

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

    public Role getConferenceRole() {
        return conferenceRole;
    }

    public void setConferenceRole(Role conferenceRole) {
        this.conferenceRole = conferenceRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public ConferenceRole(){

    }

    public ConferenceRole(Role conferenceRole){
        this.conferenceRole = conferenceRole;
    }

    public void update(Role update){
        this.conferenceRole = update;
    }

    public void updateContent(ConferenceRole update){
        this.conferenceRole = update.conferenceRole;
        this.user = update.user;
        this.conference = update.conference;
    }

    public static class Builder {
        private ConferenceRole built;

        public Builder(Role conferenceRole) {
            built = new ConferenceRole();
            built.conferenceRole = conferenceRole;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }

        public ConferenceRole build() {
            return built;
        }
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
