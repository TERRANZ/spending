package ru.terra.spending.web.security;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ru.terra.spending.db.entity.User;
import ru.terra.spending.engine.UsersEngine;

public class TUserDetailService implements UserDetailsService
{
	private UsersEngine ue = new UsersEngine();

	private static final Logger logger = LoggerFactory.getLogger(TUserDetailService.class);

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		logger.info("loadUserByUsername " + userName);
		User user = ue.findUserByName(userName);
		if (user != null)
		{
			logger.info("loadUserByUsername : user found!");
			TUserDetails ret = new TUserDetails();
			ret.setIUser(user);
			return ret;
		}
		else
		{
			logger.info("loadUserByUsername : user NOT found!");
			throw new UsernameNotFoundException(userName);
		}
	}
}
