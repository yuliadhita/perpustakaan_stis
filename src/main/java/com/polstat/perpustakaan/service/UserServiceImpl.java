package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.UserDto;
import com.polstat.perpustakaan.entity.User;
import com.polstat.perpustakaan.mapper.UserMapper;
import com.polstat.perpustakaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Encode password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // If no role is provided, assign the default role
        if (userDto.getRole() == null || userDto.getRole().isEmpty()) {
            userDto.setRole("MAHASISWA");  // Default role is "MAHASISWA"
        }

        // Create User entity and save it
        User user = userRepository.save(UserMapper.mapToUser(userDto));

        // Map User entity back to UserDto
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        // Retrieve user by email
        User user = userRepository.findByEmail(email);
        return UserMapper.mapToUserDto(user);
    }
}
