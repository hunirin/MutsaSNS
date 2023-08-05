package com.example.project_2_leegwanghun.dto;

import com.example.project_2_leegwanghun.entity.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {

    private Long id;
    private String username;
    private String password;

    private String profileImg;
    private String email;
    private String phone;

    private String token;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    public static UserDto fromEntity(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .profileImg(entity.getProfileImg())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .token(entity.getToken())
                .build();
    }

    public UserEntity newEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setProfileImg(profileImg);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setToken(token);
        return entity;
    }
}
