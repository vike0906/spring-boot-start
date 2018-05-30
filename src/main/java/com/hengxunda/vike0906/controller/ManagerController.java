package com.hengxunda.vike0906.controller;


import com.hengxunda.vike0906.common.response.CommonResponse;
import com.hengxunda.vike0906.config.shiro.OAuth2Token;
import com.hengxunda.vike0906.config.shiro.ShiroUtils;
import com.hengxunda.vike0906.entity.UserM;
import com.hengxunda.vike0906.service.UserMService;
import com.hengxunda.vike0906.utils.EncryptionUtil;
import com.hengxunda.vike0906.utils.TokenGeneratorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestController
@Api(tags = "后台管理")
@RequestMapping("manager")
public class ManagerController {

    @Autowired
    private UserMService userMService;

    @Value("${token.expireTime}")
    private int TOKENEXPIRETIME;

    @ApiOperation("登录管理后台")
    @PostMapping("login")
    public CommonResponse login(
            @ApiParam(value = "登录名", required = true)@RequestParam String loginName,
            @ApiParam(value = "登录密码",required = true)@RequestParam String password,
            HttpServletRequest request
    ){
        UserM userM = userMService.findUserMByUserName(loginName);
        try {
            if(userM == null ){
                throw new IllegalAccessException("用户名不存在");
            }

            if(!userM.getPassword().equals(EncryptionUtil.MD5(password))){
                throw new IllegalAccessException("登录密码错误");
            }
            if(userM.getStatus() != 0){
                throw new IllegalAccessException("用户已被锁定");
            }
        }catch (IllegalAccessException ie){
            return new CommonResponse(500,ie.getMessage());
        }
        /**
        * 更新token
        * */
        String token = TokenGeneratorUtil.generateValue();
        Date expireTime = new Date(new Date().getTime()+TOKENEXPIRETIME*1000);
        userM.setToken(token).setTokenExpireTime(expireTime)
                .setIp(request.getRemoteAddr());
        userMService.save(userM);

        Subject subject = ShiroUtils.getSubject();
        try {

            subject.login(new OAuth2Token(token,true));
        }
        catch (UnknownAccountException e3){
            return new CommonResponse(500,e3.getMessage());
        }
        catch (IncorrectCredentialsException e1)
        {
            return new CommonResponse(500,e1.getMessage());
        }
        catch (LockedAccountException e2)
        {
            return new CommonResponse(500,e2.getMessage());
        }

        return new CommonResponse(token);
    }
}
