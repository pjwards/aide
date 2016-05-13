package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.enums.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    List<Conference> findAllByStatus(Status status);
}
