package com.pjwards.aide.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
public class Pass {

    public static final int MAX_LENGTH_TAG = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = MAX_LENGTH_TAG, unique = true, nullable = false)
    private String tag;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public Pass setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Pass setUser(User user) {
        this.user = user;
        return this;
    }

    public Pass() {
    }

    public void update(Pass updated) {
        this.tag = updated.tag;
        this.user = updated.user;
    }

    public static class Builder {
        private Pass built;

        public Builder(String tag) {
            built = new Pass();
            built.tag = tag;
        }

        public Pass build() {
            return built;
        }

        public Builder user(User user) {
            built.user = user;
            return this;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
