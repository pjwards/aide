package com.pjwards.aide.service.currentuser;

import com.pjwards.aide.domain.CurrentUser;

public interface CurrentUserService {
    boolean canAccessUser(CurrentUser currentUser, Long userId);
}
