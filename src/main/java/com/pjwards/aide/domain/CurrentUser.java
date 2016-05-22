package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.Role;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Set;

public class CurrentUser extends org.springframework.security.core.userdetails.User{
    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }

    public String getName() {
        return user.getName();
    }

    public String getCompany(){
        return user.getCompany();
    }

    public Assets getAssets(){ return user.getAssets(); }

    public String getDescription(){return user.getDescription(); }

    public Set<ConferenceRole> getConferenceRoleSet(){ return user.getConferenceRoleSetUser(); }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
