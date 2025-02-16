package com.example.cqrsbankingapp.web.security;

import com.example.cqrsbankingapp.domain.model.Client;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Data
public class SecurityUser implements UserDetails {

    private final UUID id;
    private final String username;
    private final String password;
    private final Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(final UUID id,
                        final String username,
                        final String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = new ArrayList<>();
    }

    public SecurityUser(Client client) {
        this(
                client.getId(),
                client.getUsername(),
                client.getPassword()
        );
        this.authorities.add(
                mapToGrantedAuthorities("ROLE_USER")
        );
    }

    private static SimpleGrantedAuthority mapToGrantedAuthorities(String role) {
        return new SimpleGrantedAuthority(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
