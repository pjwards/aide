package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    @Query(value = "select COUNT(*) from Sponsor s where s.conference_id = ?1", nativeQuery = true)
    int countWhereConference(Long id);

    @Query(value = "select * from Sponsor s where s.conference_id = ?1", nativeQuery = true)
    List<Sponsor> findAllWhereConference(Long id);
}
