package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    List<Conference> findAllByStatus(Status status);
}
