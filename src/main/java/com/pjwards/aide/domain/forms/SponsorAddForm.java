package com.pjwards.aide.domain.forms;


import com.pjwards.aide.domain.Conference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;



public class SponsorAddForm {
    @NotEmpty
    private String slug = "";

    @NotEmpty
    private String name = "";

    @NotNull
    private Integer rank = 0;

    private String url = "";

    private String description = "";

    private Conference conference;

    private MultipartFile assets;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public MultipartFile getAssets() {
        return assets;
    }

    public void setAssets(MultipartFile assets) {
        this.assets = assets;
    }
}
