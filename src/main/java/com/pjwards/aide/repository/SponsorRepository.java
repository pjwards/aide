package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}
