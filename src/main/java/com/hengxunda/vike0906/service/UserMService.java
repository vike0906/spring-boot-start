package com.hengxunda.vike0906.service;


import com.hengxunda.vike0906.entity.UserM;

public interface UserMService {
    UserM findUserMByUserName(String userName);

    UserM findUserMByToken(String token);

    UserM save(UserM userM);
}
