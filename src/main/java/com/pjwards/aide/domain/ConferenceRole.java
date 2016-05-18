package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private User user;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return conferenceRole;
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

    public void update(Role conferenceRole){
        this.conferenceRole = conferenceRole;
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

        public Builder user(User user){
            built.user = user;
            return this;
        }

        public Builder conference(Conference conference){
            built.conference = conference;
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
