package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.*;

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
            fetch = FetchType.EAGER
    )
//    @JsonIgnore
    @JsonBackReference
    private Set<Program> programs;

    @OneToMany(
            targetEntity = Session.class,
            mappedBy = "room",
            fetch = FetchType.EAGER
    )
//    @JsonIgnore
    @JsonBackReference
    private Set<Session> sessions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MANAGER",
            joinColumns = @JoinColumn(name = "ROOM_ID_FRK"),
            inverseJoinColumns = @JoinColumn(name = "MANAGER_ID_FRK")
    )
    @JsonManagedReference
    private Set<User> managerSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PARTICIPANT_ROOM",
            joinColumns = @JoinColumn(name = "ROOM_ID_FRK"),
            inverseJoinColumns = @JoinColumn(name = "PARTICIPANT_ID_FRK")
    )
    @JsonManagedReference
    private Set<User> participants;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonManagedReference
    private Conference conference;

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

    public List<Program> getPrograms() {
        if (programs == null) return null;
        List<Program> list = new ArrayList<>(programs);
        Collections.sort(list, new ProgramCompare());
        return list;
    }

    public List<Program> getPrograms(long conferenceId) {
        if (programs == null) return null;
        List<Program> list = new ArrayList<>();

        for (Program program : programs) {
            if (program.getDate().getConference().getId().equals(conferenceId)) {
                list.add(program);
            }
        }

        Collections.sort(list, new ProgramCompare());
        return list;
    }

    class ProgramCompare implements Comparator<Program> {
        @Override
        public int compare(Program arg0, Program arg1) {
            if (arg0.getDate().getDay().equals(arg1.getDate().getDay())) {
                LocalTime begin0 = new LocalTime(arg0.getBegin());
                LocalTime begin1 = new LocalTime(arg1.getBegin());
                return begin0.compareTo(begin1);
            }
            return arg0.getDate().getDay().compareTo(arg1.getDate().getDay());
        }
    }

    public Room setPrograms(Set<Program> programs) {
        this.programs = programs;
        return this;

    }

    public List<Session> getSessions() {
        if (sessions == null) return null;
        List<Session> list = new ArrayList<>(sessions);
        Collections.sort(list, new SessionCompare());
        return list;
    }

    public List<Session> getSessions(long conferenceId) {
        if (sessions == null) return null;
        List<Session> list = new ArrayList<>();

        for (Session session : sessions) {
            if (session.getProgram().getDate().getConference().getId().equals(conferenceId)) {
                list.add(session);
            }
        }

        Collections.sort(list, new SessionCompare());
        return list;
    }

    class SessionCompare implements Comparator<Session> {
        @Override
        public int compare(Session arg0, Session arg1) {
            if (arg0.getProgram().getDate().getDay().equals(arg1.getProgram().getDate().getDay())) {
                LocalTime begin0 = new LocalTime(arg0.getProgram().getBegin());
                LocalTime begin1 = new LocalTime(arg1.getProgram().getBegin());
                return begin0.compareTo(begin1);
            }
            return arg0.getProgram().getDate().getDay().compareTo(arg1.getProgram().getDate().getDay());
        }
    }

    public Room setSessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Set<User> getManagerSet() {
        return managerSet;
    }

    public Room setManagerSet(Set<User> managerSet) {
        this.managerSet = managerSet;
        return this;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public Room setParticipants(Set<User> participants) {
        this.participants = participants;
        return this;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
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

        public Builder conference(Conference conference) {
            built.conference = conference;
            return this;
        }

        public Room build() {
            return built;
        }
    }

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }*/
}
