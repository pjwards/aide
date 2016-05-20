package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long>, JpaSpecificationExecutor<Conference> {
    List<Conference> findAllByStatus(Status status);
    Page<Conference> findAll(Specification<Conference> spec, Pageable pageable);
}
