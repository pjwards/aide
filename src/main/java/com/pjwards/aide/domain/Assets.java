package com.pjwards.aide.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String realPath;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private Integer downloadCount;

    @OneToOne
    @JsonManagedReference
    User user;

    @OneToOne
    @JsonManagedReference
    Sponsor sponsor;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    @JsonManagedReference
    private Conference conference;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRealPath() {
        return realPath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public User getUser() {
        return user;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public Conference getConference() {
        return conference;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assets setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public Assets setConference(Conference conference) {
        this.conference = conference;
        return this;
    }

    public Assets(){

    }

    public Assets(Assets assets){
        this.fileName = assets.fileName;
        this.realPath = assets.realPath;
        this.fileSize = assets.fileSize;
        this.downloadCount = assets.downloadCount;
    }

    public void update(Assets updated){
        this.fileName = updated.fileName;
        this.realPath = updated.realPath;
        this.fileSize = updated.fileSize;
        this.downloadCount = updated.downloadCount;
    }

    public void update(String fileName, String realPath, Long fileSize, Integer downloadCount){
        this.fileName = fileName;
        this.realPath = realPath;
        this.fileSize = fileSize;
        this.downloadCount = downloadCount;
    }

    public static class Builder {
        private Assets built;

        public Builder(String fileName, String realPath, Long fileSize, Integer downloadCount) {
            built = new Assets();
            built.fileName = fileName;
            built.realPath = realPath;
            built.fileSize = fileSize;
            built.downloadCount = downloadCount;
        }

        public Builder user(User user){
            built.user = user;
            return this;
        }

        public Builder sponsor(Sponsor sponsor){
            built.sponsor = sponsor;
            return this;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }

        public Builder conference(Conference conference) {
            built.conference = conference;
            return this;
        }

        public Assets build() {
            return built;
        }
    }

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }*/
}
