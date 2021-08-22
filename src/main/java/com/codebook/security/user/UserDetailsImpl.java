package com.codebook.security.user;

import com.codebook.domain.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter @Getter
public class UserDetailsImpl implements Serializable,UserDetails {

    private static final long serialVersionUID = 521395021292444263L;

    private String username;
    private String password;
    private String role;
    private String status;
    private String token;
    private String nickname;

    public UserDetailsImpl(MemberDTO memberDTO){
        this.setUsername(memberDTO.getEmail());
        this.setPassword(memberDTO.getPassword());
        this.setRole(memberDTO.getRole());
        this.setStatus(memberDTO.getStatus());
        this.setToken(memberDTO.getToken());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(this.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.getStatus().equals(Status.BLOCKED.name());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.getStatus().equals(Status.BLOCKED.name());
    }
}
