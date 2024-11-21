package com.hemiplegia.server.dto;

import com.hemiplegia.server.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
public class UserDto {
    private String username;
    private String password;

    public UserEntity toEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .username(this.username)
                .password(passwordEncoder.encode(this.password))
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(this.username)
                .password(this.password)
                .build();
    }
}
