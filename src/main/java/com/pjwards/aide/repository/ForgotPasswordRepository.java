package com.pjwards.aide.repository;

import com.pjwards.aide.domain.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long>, QueryDslPredicateExecutor<ForgotPassword> {
    ForgotPassword findOneByKeyHash(String keyHash);
}
