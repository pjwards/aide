package com.pjwards.aide.repository;

import com.pjwards.aide.domain.Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetsRepository extends JpaRepository<Assets, Long> {
}
