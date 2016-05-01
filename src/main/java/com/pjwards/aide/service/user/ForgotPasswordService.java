package com.pjwards.aide.service.user;

import com.pjwards.aide.domain.ForgotPassword;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.ValidEntity;
import com.pjwards.aide.domain.forms.ForgotPasswordForm;

public interface ForgotPasswordService {
    Iterable<ForgotPassword> findAllByUserANDBYValidEntity(User user, ValidEntity validEntity);

    ForgotPassword create(User user);

    User resetPassword(ForgotPasswordForm form, ForgotPassword forgotPassword);
}
