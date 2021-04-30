package com.cda.cyberpik.security.dto;

import com.cda.cyberpik.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private Long userDetailsId;
    private String userName;
    private String password;
    private boolean active;
    private Collection<GrantedAuthority> role;

    public MyUserDetails(UserAccount userAccount) {
        this.userDetailsId = userAccount.getUserAccountId();
        this.userName = userAccount.getUserName();
        this.password = userAccount.getPassword();
        this.active = !userAccount.isArchived();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String role = userAccount.isAdmin() ? "ADMIN" : "USER";
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        this.role = Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return active;
    }
}