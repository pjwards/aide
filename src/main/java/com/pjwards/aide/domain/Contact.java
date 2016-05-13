package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pjwards.aide.domain.enums.ContactType;

import javax.persistence.*;

@Entity
public class Contact {
    public static final int MAX_LENGTH_URL = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @Column(nullable = false, length = MAX_LENGTH_URL)
    private String url;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonManagedReference
    private Conference conference;

    public Long getId() {
        return id;
    }

    public ContactType getType() {
        return type;
    }

    public Contact setType(ContactType type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        if (type == ContactType.EMAIL) return url;
        if (url == null || url.startsWith("http://") || url.startsWith("https://")) return url;
        return "http://" + url;
    }

    public Contact setUrl(String url) {
        this.url = url;
        return this;
    }

    public Conference getConference() {
        return conference;
    }

    public Contact setConference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public Contact() {
    }

    public void update(Contact updated) {
        this.type = updated.type;
        this.url = updated.url;
    }

    public static class Builder {
        private Contact built;

        public Builder(ContactType type, String url) {
            built = new Contact();
            built.type = type;
            built.url = url;
        }

        public Builder conference(Conference conference) {
            built.conference = conference;
            return this;
        }

        public Contact build() {
            return built;
        }
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
