package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDate;

    @Column(length = 100)
    private String company;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID_FRK"),
            inverseJoinColumns = @JoinColumn(name = "CONFERENCE_ROLE_ID_FRK")
    )
    private Set<ConferenceRole> conferenceRoleSet;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
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

    public Role getRole() { return role; }

    public Set<ConferenceRole> getConferenceRoleSet(){ return conferenceRoleSet; }

    public void update(User updated) {
        this.name = updated.name;
        this.company = updated.company;
        this.email = updated.email;
        this.password = updated.password;
        this.lastDate = updated.lastDate;
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

        public User build() {
            return built;
        }
    }

}
