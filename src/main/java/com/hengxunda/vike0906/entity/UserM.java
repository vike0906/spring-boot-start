package com.hengxunda.vike0906.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "m_user")
public class UserM implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String userName;//登录名
    private String name;//真实姓名
    private String password;
    private String token;
    @Column(name = "token_expire_time")
    private Date tokenExpireTime;
    private int status;
    @Column(name = "last_login_ip", columnDefinition = "最后一次登录的IP地址")
    private String ip;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m_user_role", joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;


}
