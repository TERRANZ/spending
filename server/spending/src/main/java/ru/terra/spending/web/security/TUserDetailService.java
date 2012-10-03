package ru.terra.spending.web.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ru.terra.spending.db.entity.User;
import ru.terra.spending.engine.UsersEngine;

public class TUserDetailService implements UserDetailsService
{

	@Inject
	private UsersEngine ue;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		User user = ue.findUserByName(userName);
		if (user != null)
		{
			TUserDetails ret = new TUserDetails();
			ret.setIUser(user);
			return ret;
		}
		else
		{
			throw new UsernameNotFoundException(userName);
		}
	}
}
