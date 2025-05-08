package com.job_portal_backend.security;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.job_portal_backend.enums.Role;
import com.job_portal_backend.models.User;
import com.job_portal_backend.repository.UserRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    // @Override
    // public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

    //     User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
    //             .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));

    //             Set<GrantedAuthority> authorities = user.getRoles().stream()
    //             .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
    //             .collect(Collectors.toSet());
            

    //     return new org.springframework.security.core.userdetails.User(
    //             usernameOrEmail,
    //             user.getPassword(),
    //             authorities
    //     );
    //}

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }
}