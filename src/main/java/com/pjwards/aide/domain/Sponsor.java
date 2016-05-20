package com.pjwards.aide.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
public class Sponsor {
    public static final int MAX_LENGTH_STRING = 100;
    public static final int MAX_LENGTH_URL = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAX_LENGTH_STRING)
    private String slug;

    @Column(nullable = false, length = MAX_LENGTH_STRING)
    private String name;

    @Column(length = MAX_LENGTH_URL)
    private String url;

    @Column(nullable = false)
    private int rank;

    @Lob()
    @Column()
    private String description;

    @OneToOne(
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private Assets assets;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonManagedReference
    private Conference conference;

    public int getRank() {
        return rank;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        if (url == null || url.equals("") || url.startsWith("http://") || url.startsWith("https://")) return url;
        return "http://" + url;
    }

    public String getDescription() {
        return description;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Sponsor(){

    }

    public Sponsor(Sponsor sponsor){
        this.slug = sponsor.slug;
        this.name = sponsor.name;
        this.url = sponsor.url;
        this.description = sponsor.description;
        this.rank = sponsor.rank;
    }

    public void update(Sponsor updated){
        this.slug = updated.slug;
        this.name = updated.name;
        this.url = updated.url;
        this.description = updated.description;
        this.rank = updated.rank;
    }

    public void update(String slug, String name, String url, String description, int rank){
        this.slug = slug;
        this.name = name;
        this.url = url;
        this.description = description;
        this.rank = rank;
    }

    public static class Builder {
        private Sponsor built;

        public Builder(String slug, String name, int rank) {
            built = new Sponsor();
            built.name = name;
            built.slug = slug;
            built.rank = rank;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }

        public Builder url(String url){
            built.url = url;
            return this;
        }

        public Builder assets(Assets assets){
            built.assets = assets;
            return this;
        }

        public Builder conferences(Conference conference){
            built.conference = conference;
            return this;
        }

        public Sponsor build() {
            return built;
        }
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
