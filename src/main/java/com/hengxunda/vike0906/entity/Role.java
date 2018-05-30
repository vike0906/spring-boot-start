package com.hengxunda.vike0906.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "m_role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String role;
    private String description;
//    @ManyToOne
//    @JoinTable(name = "m_user_role", joinColumns = {@JoinColumn(name = "role_id")},inverseJoinColumns = {@JoinColumn(name = "user_id")})
//    private UserM user;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private List<Permission> permissions;

}
