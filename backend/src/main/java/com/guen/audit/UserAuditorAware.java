package com.guen.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        AbstractAuthenticationToken authentication  = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        System.out.println("line-서버추가");

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        User user = (User)authentication.getPrincipal();
        String auditor = UUID.fromString(user.getUsername()).toString();

        return Optional.of(auditor);
    }


}
