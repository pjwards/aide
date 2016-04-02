package com.pjwards.aide.service.conferencerole;


import com.pjwards.aide.domain.ConferenceRole;
import com.pjwards.aide.exception.ConferenceRoleNotFoundException;

import java.util.List;

public interface ConferenceRoleService {
    List<ConferenceRole> findAll();

    ConferenceRole add(ConferenceRole added);

    ConferenceRole findById(Long id) throws ConferenceRoleNotFoundException;

    ConferenceRole update(ConferenceRole updated) throws ConferenceRoleNotFoundException;

    ConferenceRole deleteById(Long id) throws ConferenceRoleNotFoundException;

}
