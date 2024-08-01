package com.khactuong.lakesidehotel.service;

import java.util.List;

import com.khactuong.lakesidehotel.model.entity.User;

public interface IUserService {
    User registerUser(User user);

    List<User> getUsers();

    void deleteUser(String email);

    User getUser(String email);
}
