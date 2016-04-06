package com.pjwards.aide.service.sponsor;


import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.exception.SponsorNotFoundException;

import java.util.List;

public interface SponsorService {
    public List<Sponsor> findAll();

    public Sponsor add(Sponsor added);

    public Sponsor findById(Long id) throws SponsorNotFoundException;

    public Sponsor update(Sponsor updated) throws SponsorNotFoundException;

    public Sponsor deleteById(Long id) throws SponsorNotFoundException;
}
