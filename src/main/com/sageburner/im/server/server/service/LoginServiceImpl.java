package com.sageburner.im.server.server.service;

import com.sageburner.im.server.server.dao.UserDao;
import com.sageburner.im.server.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Ryan on 10/16/2014.
 */
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private UserDao userDao;

//    public LoginServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        this.userDao.addUser(user);
        logger.info("User added successfully, User Details = " + user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
        logger.info("User updated successfully, User Details = " + user);
    }

    @Override
    public List<User> listUsers() {
        List<User> userList = userDao.listUsers();
        for (User user: userList) {
            logger.info("User List:: " + user);
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            logger.info("User retrieved successfully, User details = " + user);
            return user;
        } else {
            logger.info("User not found, User details = " + user);
            return null;
        }
    }

    @Override
    public void removeUser(String username) {
        userDao.removeUser(username);
        logger.info("Attempted to remove user. Username = " + username + ".  I need to add validation here!");
    }
}
