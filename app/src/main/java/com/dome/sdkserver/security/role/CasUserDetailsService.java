package com.dome.sdkserver.security.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CasUserDetailsService implements UserDetailsService, Serializable {

	private Logger logger = LoggerFactory.getLogger(CasUserDetailsService.class);

	private static final long serialVersionUID = -2157776263837215188L;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		CasUserDetail out=null;
		List<String> roles = UserInterfaceUtil.getUserRoleByUserName(username);
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String ur : roles) {
			authorities.add(new GrantedAuthorityImpl(ur));
		}
		// 获取用户基本信息
		Map<String, Object> map = UserInterfaceUtil.getUserBaseInfo(username);
		if (map != null) {
			long userId = (Integer) map.get(UserInterfaceUtil.USER_HYID_ID_KEY);
			long casId= (Integer) map.get(UserInterfaceUtil.USER_CAS_ID_KEY);
			String mobilePhone=(String)map.get(UserInterfaceUtil.USER_MOBILEPHONE_KEY);
			boolean isEnabled = (Boolean) map.get(UserInterfaceUtil.USER_ENABLE_KEY);
			logger.info("userId:" + userId);
			out= new CasUserDetail(userId, casId, username, mobilePhone, isEnabled, authorities);
		}
		return out;
	}
}