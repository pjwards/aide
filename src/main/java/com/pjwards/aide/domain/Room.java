package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Room {

    public static final int MAX_LENGTH_NAME = 50;
    public static final int MAX_LENGTH_LOCATION = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Column(nullable = false, length = MAX_LENGTH_LOCATION)
    private String location;

    @Lob()
    @Column(nullable = false)
    private String description;

    @OneToMany(
            targetEntity = Program.class,
            mappedBy = "room",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Program> programList;

    @OneToMany(
            targetEntity = Session.class,
            mappedBy = "room",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Session> sessionList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "MANAGER",
            joinColumns = @JoinColumn(name = "ROOM_ID_FRK"),
            inverseJoinColumns = @JoinColumn(name = "MANAGER_ID_FRK")
    )
    private Set<User> managerSet;

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Room setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Room setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Room setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public Room setProgramList(List<Program> programList) {
        this.programList = programList;
        return this;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public Room setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
        return this;
    }

    public Set<User> getManagerSet() {
        return managerSet;
    }

    public Room setManagerSet(Set<User> managerSet) {
        this.managerSet = managerSet;
        return this;
    }

    public void update(Room updated) {
        this.name = updated.name;
        this.location = updated.location;
        this.description = updated.description;
    }

    public static class Builder {
        private Room built;

        public Builder(String name, String location, String description) {
            built = new Room();
            built.name = name;
            built.location = location;
            built.description = description;
        }

        public Room build() {
            return built;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
