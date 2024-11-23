package com.khactuong.hotel.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.khactuong.hotel.exception.UserAlreadyExistsException;
import com.khactuong.hotel.model.entity.Role;
import com.khactuong.hotel.model.entity.User;
import com.khactuong.hotel.model.repository.RoleRepository;
import com.khactuong.hotel.model.repository.UserRepository;
import com.khactuong.hotel.service.IUserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }

        // Ensure the password encoder is properly initialized
        if (passwordEncoder == null) {
            throw new IllegalStateException("Password encoder not initialized");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> userRoleOpt = roleRepository.findByName("ROLE_USER");
        Role userRole = userRoleOpt.get();
        user.setRoles(Collections.singletonList(userRole));

        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if (theUser != null) {
            userRepository.deleteByEmail(email);
        }

    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
