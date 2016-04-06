package com.pjwards.aide.domain.builder;


import com.pjwards.aide.domain.Sponsor;
import org.springframework.test.util.ReflectionTestUtils;

public class SponsorBuilder {
    private Sponsor model;

    public SponsorBuilder(){
        model = new Sponsor();
    }

    public SponsorBuilder id(Long id){
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public SponsorBuilder slug(String slug){
        model.update(slug, model.getName(), model.getUrl(), model.getDescription());
        return this;
    }

    public SponsorBuilder name(String name){
        model.update(model.getSlug(), name, model.getUrl(), model.getDescription());
        return this;
    }

    public SponsorBuilder url(String url){
        model.update(model.getSlug(), model.getName(), url, model.getDescription());
        return this;
    }

    public SponsorBuilder description(String description){
        model.update(model.getSlug(), model.getName(), model.getUrl(), description);
        return this;
    }

    public Sponsor build(){
        return model;
    }
}
