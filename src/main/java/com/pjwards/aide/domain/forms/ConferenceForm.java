package com.pjwards.aide.domain.forms;

import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.Charge;
import com.pjwards.aide.domain.enums.ContactType;
import com.pjwards.aide.domain.enums.Status;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

import static com.pjwards.aide.domain.enums.ContactType.*;

public class ConferenceForm {

    @NotEmpty
    private String name = "";

    @NotEmpty
    private String slogan = "";

    @NotEmpty
    private String description = "";

    private Status status = Status.OPEN;

    private Charge charge = Charge.FREE;

    private Integer price = 0;

    private User host;

    private String location = "";

    private String locationUrl = "";

    private Double lat = 0.0;

    private Double lan = 0.0;

    private String email = "";

    private String facebook = "";

    private String twitter = "";

    private String github = "";

    private String googlePlus = "";

    private List<MultipartFile> assets;

    private String disqus = "";

    private Set<Assets> oldAssets;

    public ConferenceForm() {
    }

    public ConferenceForm(Conference conference) {
        this.name = conference.getName();
        this.slogan = conference.getSlogan();
        this.description = conference.getDescription();
        this.status = conference.getStatus();
        this.charge = conference.getCharge();
        this.price = conference.getPrice();
        this.host = conference.getHost();
        this.location = conference.getLocation() != null ? conference.getLocation() : "";
        this.locationUrl = conference.getLocationUrl() != null ? conference.getLocationUrl() : "";
        this.lat = conference.getLat();
        this.lan = conference.getLan();
        this.disqus = conference.getDisqus() != null ? conference.getDisqus() : "";
        this.oldAssets = conference.getAssetsSet();

        for (Contact contact : conference.getContacts()) {
            switch (contact.getType()) {
                case EMAIL:
                    this.email = contact.getUrl();
                    break;
                case FACEBOOK:
                    this.facebook = contact.getUrl();
                    break;
                case TWITTER:
                    this.twitter = contact.getUrl();
                    break;
                case GITHUB:
                    this.github = contact.getUrl();
                    break;
                case GOOGLEPLUS:
                    this.googlePlus = contact.getUrl();
                    break;
            }
        }


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLan() {
        return lan;
    }

    public void setLan(Double lan) {
        this.lan = lan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public Status[] getStatusList() {
        return Status.values();
    }

    public Charge[] getChargeList() {
        return Charge.values();
    }

    public List<MultipartFile> getAssets() {
        return assets;
    }

    public void setAssets(List<MultipartFile> assets) {
        this.assets = assets;
    }

    public String getDisqus() {
        return disqus;
    }

    public void setDisqus(String disqus) {
        this.disqus = disqus;
    }

    public Set<Assets> getOldAssets() {
        return oldAssets;
    }

    public void setOldAssets(Set<Assets> oldAssets) {
        this.oldAssets = oldAssets;
    }
}
