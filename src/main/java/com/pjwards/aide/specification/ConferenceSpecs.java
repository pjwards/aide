package com.pjwards.aide.specification;

import com.pjwards.aide.domain.Conference;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class ConferenceSpecs {
    public static Specification<Conference> nameLike(final String keyword) {
        return new Specification<Conference>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Conference> root,
                                                                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name"), "%" + keyword + "%");
            }
        };
    }
}
