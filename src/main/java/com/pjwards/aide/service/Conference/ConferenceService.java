package com.pjwards.aide.service.conference;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.exception.ConferenceNotFoundException;

import java.util.List;

public interface ConferenceService {

    public List<Conference> findAll();

    public Conference add(Conference created);

    public Conference findById(Long id) throws ConferenceNotFoundException;

    public Conference update(Conference updated) throws ConferenceNotFoundException;

    public Conference deleteById(Long id) throws ConferenceNotFoundException;
}
