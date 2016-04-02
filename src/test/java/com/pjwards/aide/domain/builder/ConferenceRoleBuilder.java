package com.pjwards.aide.domain.builder;


import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.domain.enums.Role;
import org.springframework.test.util.ReflectionTestUtils;

public class ConferenceRoleBuilder {
    private ConferenceRole model;

    public ConferenceRoleBuilder(){
        model = new ConferenceRole();
    }

    public ConferenceRoleBuilder id(Long id){
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public ConferenceRoleBuilder conferenceRole(Role conferenceRole){
        model.update(conferenceRole);
        return this;
    }

    public ConferenceRole build(){
        return model;
    }
}
