package com.pjwards.aide.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;

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
    private List<Program> programList;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public Room setId(Long id) {
        this.id = id;
        return this;
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

    public String getDescription() {
        return description;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public Conference getConference() {
        return conference;
    }

    public void update(Room updated) {
        this.name = updated.name;
        this.location = updated.location;
        this.description = updated.description;
    }

    public void update(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
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

        public Builder conference(Conference conference) {
            built.conference = conference;
            return this;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
