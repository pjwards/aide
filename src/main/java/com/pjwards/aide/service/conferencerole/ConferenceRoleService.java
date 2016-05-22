package com.pjwards.aide.service.conferencerole;


import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.exception.ConferenceRoleNotFoundException;

import java.util.List;

public interface ConferenceRoleService {
    public List<ConferenceRole> findAll();

    public ConferenceRole add(ConferenceRole added);

    public ConferenceRole findById(Long id) throws ConferenceRoleNotFoundException;

    public ConferenceRole update(ConferenceRole updated) throws ConferenceRoleNotFoundException;

    public ConferenceRole deleteById(Long id) throws ConferenceRoleNotFoundException;

    public List<ConferenceRole> findByConference(Conference conference);

    public ConferenceRole updateContent(ConferenceRole updated) throws ConferenceRoleNotFoundException;
}
