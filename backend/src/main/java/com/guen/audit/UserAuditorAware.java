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
        System.out.println("서버수정");

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        System.out.println("서버수정");

        User user = (User)authentication.getPrincipal();
        String auditor = UUID.fromString(user.getUsername()).toString();

        return Optional.of(auditor);
    }


}
