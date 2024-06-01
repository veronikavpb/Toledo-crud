package org.example.enity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
