package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.Role;

public class ConferenceRole {
    private Long id;
    private Role role;

    public Long getId() {
        return id;
    }

    public ConferenceRole setId(Long id) {
        this.id = id;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public ConferenceRole setRole(Role role) {
        this.role = role;
        return this;
    }
}
