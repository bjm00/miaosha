package com.miaosha.service;

import com.miaosha.dao.UserDao;
import com.miaosha.domain.MiaoshaUser;
import com.miaosha.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

//    public User getByPass(int pass) {
//        return userDao.getById(id);
//    }

    @Transactional
    public boolean tx() {
        User user = new User();
        user.setId(2);
        user.setName("tom");
        userDao.insert(user);

        User user1 = new User();
        user1.setId(1);
        user1.setName("jerry1");
        userDao.insert(user1);

        return true;
    }
}
