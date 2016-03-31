package com.pjwards.aide.repository;

import com.pjwards.aide.domain.ProgramDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramDateRepository extends JpaRepository<ProgramDate, Long> {
}
