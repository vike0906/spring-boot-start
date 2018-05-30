package com.hengxunda.vike0906.dao;

import com.hengxunda.vike0906.entity.UserM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMDao extends JpaRepository<UserM,Long> {

    UserM findByUserName(String userName);

    UserM findByToken(String token);
}
