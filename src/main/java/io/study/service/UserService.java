package io.study.service;

import io.study.entity.User;
import io.study.repository.UserRepository;
import io.study.reqeust.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserDto userDto) {
        userDto.encodePwd(passwordEncoder);
        userRepository.save(userDto.toEntity());
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("존재하지 않는 멤버"));
    }
}
