package com.fixflow.backend.user;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CustomUserDetails  implements UserDetails {

    private final User user;

    public CustomUserDetails (User user){
        this.user = Objects.requireNonNull(
                user, "User cannot be null"
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public  String getUsername(){
        return user.getEmail();
    }


    public Long getUserId(){
        return user.getId();
    }

    public String getFullName() {
        return user.getFullName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
