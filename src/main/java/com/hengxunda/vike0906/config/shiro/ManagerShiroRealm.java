package com.hengxunda.vike0906.config.shiro;


import com.hengxunda.vike0906.entity.Permission;
import com.hengxunda.vike0906.entity.Role;
import com.hengxunda.vike0906.entity.UserM;
import com.hengxunda.vike0906.service.UserMService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

@Slf4j
public class ManagerShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Resource
    private UserMService userMService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserM userM = (UserM) principalCollection.getPrimaryPrincipal();
        for (Role role:userM.getRoles()) {
            authorizationInfo.addRole(role.getRole());
            for (Permission permission: role.getPermissions() ) {

                authorizationInfo.addStringPermission(permission.getUrl());
            }


        }
        log.info("登录名{}，真实名{}授权成功",userM.getUserName(),userM.getName());
        return authorizationInfo;
//        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        OAuth2Token oAuth2Token = (OAuth2Token) token ;


        String _token = (String)token.getPrincipal();
        UserM userM = userMService.findUserMByToken(_token);
        if(userM == null){
            //用户不存在
            throw new UnknownAccountException("TOKEN非法");
        }
        if(!oAuth2Token.getIsLogin()&&userM.getTokenExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("请重新登录");
        }
        if(userM.getStatus() != 0){
            //用户被锁定
            throw new LockedAccountException("用户被锁定");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userM,
                _token,
                getName()
        );
        log.info("登录名{}，真实名{} 通过登录认证",userM.getUserName(),userM.getName());
        return authenticationInfo;
    }
}
