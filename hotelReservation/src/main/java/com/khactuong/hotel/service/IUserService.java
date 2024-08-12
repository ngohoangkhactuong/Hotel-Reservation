package com.khactuong.hotel.service;

import java.util.List;

import com.khactuong.hotel.model.entity.User;

public interface IUserService {
    User registerUser(User user);

    List<User> getUsers();

    void deleteUser(String email);

    User getUser(String email);
}
