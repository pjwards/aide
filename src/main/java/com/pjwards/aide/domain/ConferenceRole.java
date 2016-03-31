package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pjwards.aide.domain.enums.Role;

import javax.persistence.*;
import java.util.Set;

public class ConferenceRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role conferenceRole;

    @ManyToMany
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "CONFERENCE_ROLE_ID_FRK"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID_FRK")
    )
    @JsonBackReference
    private Set<User> userSet;

    @ManyToMany
    @JoinTable(name = "CONFERENCE_ROLE",
            joinColumns = @JoinColumn(name = "CONFERENCE_ROLE_ID_FRK"),
            inverseJoinColumns = @JoinColumn(name = "CONFERENCE_ID_FRK")
    )
    @JsonBackReference
    private Set<Conference> conferenceSet;

    public Long getId() {
        return id;
    }


    public Role getRole() {
        return conferenceRole;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public Set<Conference> getConferenceSet() {
        return conferenceSet;
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

        public Builder user(Set<User> userSet){
            built.userSet = userSet;
            return this;
        }

        public Builder conference(Set<Conference> conferenceSet){
            built.conferenceSet = conferenceSet;
            return this;
        }

        public ConferenceRole build() {
            return built;
        }
    }

}
