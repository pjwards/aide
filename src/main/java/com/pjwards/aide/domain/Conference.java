package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Conference {

    public static final int MAX_LENGTH_NAME = 50;
    public static final int MAX_LENGTH_SLOGAN = 100;
    public static final int MAX_LENGTH_LOCATION = 100;
    public static final int MAX_LENGTH_LOCATION_URL = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Column(nullable = false, length = MAX_LENGTH_SLOGAN)
    private String slogan;

    @Lob()
    @Column(nullable = false)
    private String description;

    @Column(length = MAX_LENGTH_LOCATION)
    private String location;

    @Column(length = MAX_LENGTH_LOCATION_URL)
    private String locationUrl;

    @Column()
    private double lat;

    @Column()
    private double lan;

    @OneToMany(
            targetEntity = ProgramDate.class,
            mappedBy = "conference",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private Set<ProgramDate> programDates;

    @ManyToMany(
            targetEntity = ConferenceRole.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "conferenceSet"
    )
    private Set<ConferenceRole> conferenceRoleSet;

    @OneToOne
    private User host;

    @OneToMany(
            targetEntity = Assets.class,
            mappedBy = "conference",
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private Set<Assets> assetsSet;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Conference setName(String name) {
        this.name = name;
        return this;
    }

    public String getSlogan() {
        return slogan;
    }

    public Conference setSlogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Conference setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Conference setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public Conference setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Conference setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLan() {
        return lan;
    }

    public Conference setLan(double lan) {
        this.lan = lan;
        return this;
    }

    public Set<ProgramDate> getProgramDates() {
        return programDates;
    }

    public Conference setProgramDates(Set<ProgramDate> programDates) {
        this.programDates = programDates;
        return this;
    }


    public Set<ConferenceRole> getConferenceRoleSet() {
        return conferenceRoleSet;
    }

    public Conference setConferenceRoleSet(Set<ConferenceRole> conferenceRoleSet) {
        this.conferenceRoleSet = conferenceRoleSet;
        return this;
    }

    public User getHost() {
        return host;
    }

    public Conference setHost(User host) {
        this.host = host;
        return this;
    }

    public Set<Assets> getAssetsSet() {
        return assetsSet;
    }

    public Conference setAssetsSet(Set<Assets> assetsSet) {
        this.assetsSet = assetsSet;
        return this;
    }

    public Conference() {
    }

    public void update(Conference updated) {
        this.name = updated.name;
        this.slogan = updated.slogan;
        this.description = updated.description;
        this.assetsSet = updated.assetsSet;
        this.location = updated.location;
        this.locationUrl = updated.locationUrl;
        this.lat = updated.lat;
        this.lan = updated.lan;
    }

    public static class Builder {
        private Conference built;

        public Builder(String name, String slogan, String description) {
            built = new Conference();
            built.name = name;
            built.slogan = slogan;
            built.description = description;
        }

        public Builder location(String location) {
            built.location = location;
            return this;
        }

        public Builder locationUrl(String locationUrl) {
            built.locationUrl = locationUrl;
            return this;
        }

        public Builder host(User host) {
            built.host = host;
            return this;
        }

        public Builder assets(Set<Assets> assetsSet) {
            built.assetsSet = assetsSet;
            return this;
        }

        public Builder latlan(double lat, double lan) {
            built.lat = lat;
            built.lan = lan;
            return this;
        }

        public Conference build() {
            return built;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
