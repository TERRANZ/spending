package ru.terra.spending.web.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ru.terra.spending.db.entity.User;

public class TUserDetails implements UserDetails
{
	private static final long serialVersionUID = -7717808998423068908L;
	private User user;
	
	private static final Logger logger = LoggerFactory.getLogger(TUserDetails.class);
	
	private Collection<GrantedAuthority> grantedAuthority;

	public void setIUser(User tuser)
	{

		logger.info("setIUser");		
		this.user = tuser;
		grantedAuthority = new ArrayList<GrantedAuthority>();
		grantedAuthority.add(RoleConstants.GA_USER);
	}

	public User getIUser()
	{
		return this.user;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{
		return grantedAuthority;
	}

	@Override
	public String getPassword()
	{
		return user.getPassword();
	}

	@Override
	public String getUsername()
	{
		return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	public long getId()
	{
		return user.getId();
	}

}
