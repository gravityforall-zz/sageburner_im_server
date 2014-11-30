package com.sageburner.im.server.service;

import com.sageburner.im.server.model.User;

import java.util.List;

/**
 * Created by Ryan on 10/16/2014.
 */
public interface LoginService {
    public void addUser(User user);
    public void updateUser(User user);
    public List<User> listUsers();
    public User getUserByUsername(String username);
    public User getUser(String id);
    public void removeUser(String username);
}
