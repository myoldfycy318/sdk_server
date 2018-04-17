package com.dome.sdkserver.security.role;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CasUserDetail implements UserDetails {

	private static final long serialVersionUID = 775161384030684636L;
	private Long id;
	private Long casId;
	private String username;
	private String mobilePhone;
	private boolean enabled;
	private Collection<GrantedAuthority> authorities;

	public CasUserDetail() {
	}

	public CasUserDetail(Long id, Long casId, String username,
			String mobilePhone, boolean enabled,
			Collection<GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.casId = casId;
		this.username = username;
		this.mobilePhone = mobilePhone;
		this.enabled = enabled;
		this.authorities = authorities;
	}

	public long getId() {
		return id;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Long getCasId() {
		return casId;
	}

	public void setCasId(Long casId) {
		this.casId = casId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}
