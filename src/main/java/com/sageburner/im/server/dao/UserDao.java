package com.sageburner.im.server.dao;

import com.sageburner.im.server.model.User;

import java.util.List;

/**
 * Created by Ryan on 10/16/2014.
 */
public interface UserDao {
    public Void addUser(User user);
    public Void updateUser(User user);
    public List<User> listUsers();
    public User getUserByUsername(String username);
    public Void removeUser(String username);
}
