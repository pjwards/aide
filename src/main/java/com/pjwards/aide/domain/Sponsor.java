package com.pjwards.aide.domain;


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

    @Lob()
    @Column()
    private String description;

    @OneToOne
    private Assets assets;

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
        return url;
    }

    public String getDescription() {
        return description;
    }

    public Assets getAssets() {
        return assets;
    }

    public Sponsor(){

    }

    public Sponsor(Sponsor sponsor){
        this.slug = sponsor.slug;
        this.name = sponsor.name;
        this.url = sponsor.url;
        this.description = sponsor.description;
    }

    public void update(Sponsor updated){
        this.slug = updated.slug;
        this.name = updated.name;
        this.url = updated.url;
        this.description = updated.description;
    }

    public void update(String slug, String name, String url, String description){
        this.slug = slug;
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public static class Builder {
        private Sponsor built;

        public Builder(String slug, String name) {
            built = new Sponsor();
            built.name = name;
            built.slug = slug;
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

        public Sponsor build() {
            return built;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
