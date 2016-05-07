package com.pjwards.aide.domain.builder;


import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.Role;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

public class UserBuilder {
    private User model;

    public UserBuilder(){
        model = new User();
    }

    public UserBuilder id(Long id){
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public UserBuilder name(String name){
        model.update(name, model.getEmail(), model.getPassword(), model.getCreatedDate(),
                model.getLastDate(), model.getCompany(), model.getRole(), model.getDescription());
        return this;
    }

    public UserBuilder email(String email){
        model.update(model.getName(), email, model.getPassword(), model.getCreatedDate(),
                model.getLastDate(), model.getCompany(), model.getRole(), model.getDescription());
        return this;
    }

    public UserBuilder password(String password){
        model.update(model.getName(), model.getEmail(), password,model.getCreatedDate(),
                model.getLastDate(), model.getCompany(), model.getRole(), model.getDescription());
        return this;
    }

    public UserBuilder createdDate(Date createdDate) {
        model.update(model.getName(), model.getEmail(), model.getPassword(), createdDate, model.getLastDate(),
                model.getCompany(), model.getRole(), model.getDescription());
        return this;
    }

    public UserBuilder lastDate(Date lastDate) {
        model.update(model.getName(), model.getEmail(), model.getPassword(),model.getCreatedDate(),
                lastDate, model.getCompany(), model.getRole(), model.getDescription());
        return this;
    }

    public UserBuilder company(String company){
        model.update(model.getName(), model.getEmail(), model.getPassword(),model.getCreatedDate(),
                model.getLastDate(), company, model.getRole(), model.getDescription());
        return this;
    }

    public UserBuilder role(Role role){
        model.update(model.getName(), model.getEmail(), model.getPassword(), model.getCreatedDate(),
                model.getLastDate(), model.getCompany(), role, model.getDescription());
        return this;
    }

    public UserBuilder description(String description){
        model.update(model.getName(), model.getEmail(), model.getPassword(), model.getCreatedDate(),
                model.getLastDate(), model.getCompany(), model.getRole(), description);
        return this;
    }

    public User build(){
        return model;
    }
}
