package com.pjwards.aide.service.presence;


import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Presence;
import com.pjwards.aide.exception.PresenceNotFoundException;

import java.util.List;

public interface PresenceService {
    public List<Presence> findAll();

    public Presence add(Presence added);

    public Presence findById(Long id) throws PresenceNotFoundException;

    public Presence deleteById(Long id) throws PresenceNotFoundException;

    public List<Presence> findByConference(Conference conference);

    public Presence update(Presence updated) throws PresenceNotFoundException;
}
