package com.pjwards.aide.repository;


import com.pjwards.aide.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
