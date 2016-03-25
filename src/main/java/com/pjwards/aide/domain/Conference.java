package com.pjwards.aide.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Lob()
    @Column(nullable = false)
    private String description;

    @OneToMany(
            targetEntity = ProgramDate.class,
            mappedBy = "conference",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    private List<ProgramDate> programDateList;

    @OneToMany(
            targetEntity = Room.class,
            mappedBy = "conference",
            fetch = FetchType.EAGER
    )
    private List<Room> rooms;

    @OneToMany(
            targetEntity = Program.class,
            mappedBy = "conference",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    private List<Program> programList;

    public Long getId() {
        return id;
    }

    public Conference setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Conference setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Conference setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<ProgramDate> getProgramDateList() {
        return programDateList;
    }

    public Conference setProgramDateList(List<ProgramDate> programDateList) {
        this.programDateList = programDateList;
        return this;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Conference setRooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public Conference setProgramList(List<Program> programList) {
        this.programList = programList;
        return this;
    }
}
