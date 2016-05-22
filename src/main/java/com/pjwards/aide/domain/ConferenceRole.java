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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_conferenceRole", joinColumns = { @JoinColumn(name = "conferenceRole_id_frk") },
            inverseJoinColumns = { @JoinColumn(name = "user_id_frk") })
    private Set<User> userSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "conference_conferenceRole", joinColumns = { @JoinColumn(name = "conferenceRole_id_frk") },
            inverseJoinColumns = { @JoinColumn(name = "conference_id_frk") })
    private Set<Conference> conferenceSet;

    public Long getId() {
        return id;
    }

    public Role getConferenceRole() {
        return conferenceRole;
    }

    public void setConferenceRole(Role conferenceRole) {
        this.conferenceRole = conferenceRole;
    }

    public Set<Conference> getConferenceSet() {
        return conferenceSet;
    }

    public void setConferenceSet(Set<Conference> conferenceSet) {
        this.conferenceSet = conferenceSet;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
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
        this.userSet = update.userSet;
        this.conferenceSet = update.conferenceSet;
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
