package com.pjwards.aide.repository;

import com.pjwards.aide.domain.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> { //, QueryDslPredicateExecutor<ForgotPassword>
    ForgotPassword findOneByKeyHash(String keyHash);
}
