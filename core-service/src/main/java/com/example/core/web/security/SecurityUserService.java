package com.example.core.web.security;


import com.example.common.domain.model.Client;
import com.example.core.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final ClientService clientService;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        Client client = clientService.getByUsername(username);
        return new SecurityUser(client);
    }

}
