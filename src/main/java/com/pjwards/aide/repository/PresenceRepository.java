package com.pjwards.aide.repository;


import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findAllByConferenceSet(Conference conference);
}
