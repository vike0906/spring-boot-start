package com.hengxunda.vike0906.config.shiro;

import com.hengxunda.vike0906.entity.UserM;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类 *
 * @author lwx
 */
public class ShiroUtils {


	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}


	public static Session getSession() {
		return getSubject().getSession();
	}

	public static UserM getUser() {
		return (UserM) getSubject().getPrincipal();
	}



	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return getSubject().getPrincipal() != null;
	}

	public static void logout() {
		getSubject().logout();
	}

	/**
	 * 校验登录密码是否正确
	 * @param pwd
	 * @return
	 */
	public static Boolean checkPwd(String pwd)
	{
		if(StringUtils.isBlank(pwd))
			return false;
		UserM user = getUser();
		//暂使用前端加密后的密码。updateTime 2017/09/14 10:30
		//return user.getPassword().equals(EncryptionUtil.MD5(pwd));
		return user.getPassword().equals(pwd);
	}





}
