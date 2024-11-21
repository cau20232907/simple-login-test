package com.hemiplegia.server.service;

import com.hemiplegia.server.dto.UserDto;
import com.hemiplegia.server.entity.UserEntity;
import com.hemiplegia.server.exception.UsernameAlreadyInUseException;
import com.hemiplegia.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity save(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyInUseException("이미 사용중인 ID입니다.");
        }
        return userRepository.save(userDto.toEntity(passwordEncoder));
    }

    @Override
    @Transactional
    //원래 반환값은 UserDetails
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("ID 또는 비밀번호가 틀렸습니다.");
        }
    }
}
