package com.pjwards.aide.domain;

import com.pjwards.aide.domain.enums.ValidEntity;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keyHash;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date expiredDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ValidEntity validEntity;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public ValidEntity getValidEntity() {
        return validEntity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ForgotPassword(){

    }

    public ForgotPassword(ForgotPassword forgotPassword){
        this.keyHash = forgotPassword.keyHash;
        this.expiredDate = forgotPassword.expiredDate;
        this.validEntity = forgotPassword.validEntity;
    }

    public void update(ForgotPassword updated) {
        this.keyHash = updated.keyHash;
        this.expiredDate = updated.expiredDate;
        this.validEntity = updated.validEntity;
    }

    public void update(String keyHash, ValidEntity validEntity, Date expiredDate){
        this.keyHash = keyHash;
        this.expiredDate = expiredDate;
        this.validEntity = validEntity;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setValidEntity(ValidEntity validEntity) {
        this.validEntity = validEntity;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }

    public static class Builder {
        private ForgotPassword built;

        public Builder(String keyHash, ValidEntity validEntity, Date expiredDate) {
            built = new ForgotPassword();
            built.keyHash = keyHash;
            built.validEntity = validEntity;
            built.expiredDate = expiredDate;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }

        public Builder user(User user){
            built.user = user;
            return this;
        }

        public ForgotPassword build() {
            return built;
        }
    }
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
