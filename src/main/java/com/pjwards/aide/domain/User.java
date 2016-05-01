package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjwards.aide.domain.enums.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class User {
    public static final int MAX_LENGTH_NAME = 50;
    public static final int MAX_LENGTH_COMPANY = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssz")
    private Date createdDate;

    @Lob()
    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssz")
    private Date lastDate;

    @Column(length = MAX_LENGTH_COMPANY)
    private String company;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(
            targetEntity = ConferenceRole.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "userSet"
    )
    private Set<ConferenceRole> conferenceRoleSet;

    @OneToOne
    private Assets assets;

    @ManyToMany(
            targetEntity = Room.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "managerSet"
    )
    @JsonIgnore
    private Set<Room> roomSet;

    @ManyToMany(
            targetEntity = Program.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "speakerSet"
    )
    @JsonIgnore
    private Set<Program> programSet;

    @ManyToMany(
            targetEntity = Session.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "speakerSet"
    )
    @JsonIgnore
    private Set<Session> sessionSet;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public String getCompany() {
        return company;
    }

    public Role getRole() { return role; }

    public Set<ConferenceRole> getConferenceRoleSet(){ return conferenceRoleSet; }

    public Assets getAssets() {
        return assets;
    }

    public Set<Room> getRoomSet() {
        return roomSet;
    }

    public Set<Program> getProgramSet() {
        return programSet;
    }

    public Set<Session> getSessionSet() {
        return sessionSet;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public void setConferenceRoleSet(Set<ConferenceRole> conferenceRoleSet) {
        this.conferenceRoleSet = conferenceRoleSet;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(){

    }

    public User(User user){
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.createdDate = user.createdDate;
        this.lastDate = user.lastDate;
        this.role = user.role;
        this.company = user.company;
        this.description = user.description;
    }

    public void update(User updated) {
        this.name = updated.name;
        this.company = updated.company;
        this.email = updated.email;
        this.password = updated.password;
        this.lastDate = updated.lastDate;
        this.role = updated.role;
        this.description = updated.description;
    }

    public void update(String name, String email, String password, Date createdDate, Date lastDate, String company,
                       Role role, String description){
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.lastDate = lastDate;
        this.company = company;
        this.role = role;
        this.description = description;
    }

    public static class Builder {
        private User built;

        public Builder(String name, String email, String password) {
            built = new User();
            built.name = name;
            built.email = email;
            built.password = password;
            built.createdDate = new Date();
            built.lastDate = new Date();
            built.role = Role.USER;
        }

        public Builder company(String company) {
            built.company = company;
            return this;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }

        public Builder description(String description){
            built.description = description;
            return this;
        }

        public Builder conferenceRole(Set<ConferenceRole> conferenceRoleSet){
            built.conferenceRoleSet = conferenceRoleSet;
            return this;
        }

        public Builder lastDate(Date lastDate){
            built.lastDate = lastDate;
            return this;
        }

        public Builder role(Role role) {
            built.role = role;
            return this;
        }

        public Builder assets(Assets assets){
            built.assets = assets;
            return this;
        }

        public User build() {
            return built;
        }
    }

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }*/
}
