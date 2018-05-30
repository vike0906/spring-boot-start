package com.hengxunda.vike0906.service.impl;

import com.hengxunda.vike0906.dao.UserMDao;
import com.hengxunda.vike0906.entity.UserM;
import com.hengxunda.vike0906.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMServiceImpl implements UserMService {

    @Autowired
    private UserMDao userMDao;

    @Override
    public UserM findUserMByUserName(String userName) {
        return userMDao.findByUserName(userName);
    }

    @Override
    public UserM findUserMByToken(String token) {
        return userMDao.findByToken(token);
    }

    @Override
    public UserM save(UserM userM) {
        return userMDao.save(userM);
    }
}
