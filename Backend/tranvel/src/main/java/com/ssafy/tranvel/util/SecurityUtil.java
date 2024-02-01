package com.ssafy.tranvel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    // Prevent instantiation
    private SecurityUtil() {}

//    public static Optional<String> getCurrentUserEmail() {
//
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null) {
//            logger.debug("there is no authentication in security context");
//            return Optional.empty();
//        }
//
//        String email = null;
//        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
//            email = springSecurityUser.getUsername();
//        } else if (authentication.getPrincipal() instanceof String) {
//            email = (String) authentication.getPrincipal();
//        }
//
//        return Optional.ofNullable(email);
//    }

    public static Optional<String> getCurrentUserId() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("there is no authentication in security context");
            return Optional.empty();
        }

        String id = null;
        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            id = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            id = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(id);
    }
}
