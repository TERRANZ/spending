package ru.terra.spending.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy;

import ru.terra.spending.db.entity.User;
import ru.terra.spending.web.security.TUserDetails;

public class SessionControlStrategy extends ConcurrentSessionControlStrategy
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionControlStrategy.class);

	public SessionControlStrategy(SessionRegistry sessionRegistry)
	{
		super(sessionRegistry);
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response)
	{
		// Allow the parent to create a new session if necessary
		HttpSession session = request.getSession();
		Object principal = authentication.getPrincipal();
		User user = null;
		if (principal instanceof TUserDetails)
		{
			user = ((TUserDetails) principal).getIUser();
			// UserSettingsUtil.loadSettings(iUser, session);
			// new SessionTimeRating(iUser.getId()).rate(null, 0);
			LOG.debug("!!!!!SessionControlStrategy user: " + user.getId());
		}
		super.onAuthentication(authentication, request, response);
	}
}
