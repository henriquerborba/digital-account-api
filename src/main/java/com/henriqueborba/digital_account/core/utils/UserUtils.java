package com.henriqueborba.digital_account.core.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserUtils {
    private UserUtils() {
    }

    public static UserDetails getLoggedUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
