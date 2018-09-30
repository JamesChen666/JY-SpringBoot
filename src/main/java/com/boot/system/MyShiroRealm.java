package com.boot.system;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.model.Loginaccount;
import com.boot.model.Role;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.beetl.sql.core.SQLManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 权限管理工具
 * @author chenjiang
 */
public class MyShiroRealm extends AuthorizingRealm{

	private static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	SQLManager sqlManager;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		return authorizationInfo;
	}


	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String UserName = token.getPrincipal().toString();
        Loginaccount loginaccount = sqlManager.selectSingle("shiro.findUserByUserName", Dict.create().set("UserName", UserName), Loginaccount.class);
		Role role = sqlManager.selectSingle("shiro.findRoleByUserName", Dict.create().set("UserName", UserName), Role.class);
		if (ObjectUtil.isNull(loginaccount)||ObjectUtil.isNull(role)) {
			log.error("用户不存在");
			throw new UnknownAccountException("用户不存在");
		}
		SecurityUtils.getSubject().getSession().setAttribute("User", loginaccount);
		SecurityUtils.getSubject().getSession().setAttribute("Role", role);
		AuthenticationInfo authenticationInfo =
				new SimpleAuthenticationInfo(UserName, loginaccount.getPassWord(),ByteSource.Util.bytes(loginaccount.getSalt()),getName());
		return authenticationInfo;
	}

}
