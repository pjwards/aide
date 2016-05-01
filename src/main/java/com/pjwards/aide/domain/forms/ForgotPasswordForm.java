package com.pjwards.aide.domain.forms;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class ForgotPasswordForm {
    @NotEmpty
    private String password = "";

    @NotEmpty
    private String passwordRepeated = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
