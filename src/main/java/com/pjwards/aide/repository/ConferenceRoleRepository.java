package com.pjwards.aide.repository;


import com.pjwards.aide.domain.ConferenceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoleRepository extends JpaRepository<ConferenceRole, Long> {
}
