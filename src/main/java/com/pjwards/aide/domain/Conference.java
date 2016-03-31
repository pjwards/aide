package com.pjwards.aide.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
public class Conference {

    public static final int MAX_LENGTH_NAME = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
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

    public Conference() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ProgramDate> getProgramDateList() {
        return programDateList;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public void update(Conference updated) {
        this.name = updated.name;
        this.description = updated.description;
    }

    public static class Builder {
        private Conference built;

        public Builder(String name, String description) {
            built = new Conference();
            built.name = name;
            built.description = description;
        }

        public Conference build() {
            return built;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}