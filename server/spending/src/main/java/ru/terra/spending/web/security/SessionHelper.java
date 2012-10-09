package ru.terra.spending.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ru.terra.spending.db.entity.User;

public final class SessionHelper
{
	private static final Logger logger = LoggerFactory.getLogger(SessionHelper.class);

	/**
	 * Проверяет, авторизован ли пользователь в указанном контексте безопасности
	 * 
	 * @param context Контекст безопасности
	 * @return
	 */
	public static boolean isUserAuthorized(SecurityContext context)
	{
		if (context == null)
			return false;
		Authentication auth = context.getAuthentication();
		if (auth == null)
		{
			logger.info("Authentication is null!");
			return false;
		}
		boolean isAnonymous = false;
		for (GrantedAuthority authority : auth.getAuthorities())
		{
			String authorityName = authority.getAuthority();
			logger.info("authorityName = " + authorityName);
			if (RoleConstants.ROLE_ANONYMOUS.equals(authorityName))
			{
				isAnonymous = true;
				logger.info("isAnonymous = true");
				break;
			}

		}
		return !isAnonymous;
	}

	/**
	 * Проверяет, авторизован ли текущий пользователь
	 * 
	 * @return
	 */
	public static boolean isUserCurrentAuthorized()
	{

		return isUserAuthorized(SecurityContextHolder.getContext());
	}

	/**
	 * Получает данные пользователя из указанного контекста безопасности
	 * 
	 * @param context Контекст безопасности
	 * @return
	 */
	public static User getIUser(SecurityContext context)
	{
		logger.info("getIUser");
		if (context == null || !isUserAuthorized(context))
		{
			logger.info(context == null ? "context == null" : "context is not null");
			logger.info(!isUserAuthorized(context) ? "!isUserAuthorized(context)" : "isUserAuthorized(context)");
			return null;
		}
		Authentication auth = context.getAuthentication();
		if (auth == null)
		{
			logger.info("Authentication is null!");
			return null;
		}
		Object principal = auth.getPrincipal();
		User iUser = null;
		if (principal instanceof TUserDetails)
			iUser = ((TUserDetails) principal).getIUser();
		return iUser;
	}

	/**
	 * Получает данные текущего пользователя (null, если пользователь не авторизован)
	 * 
	 * @return
	 */
	public static User getCurrentIUser()
	{
		return getIUser(SecurityContextHolder.getContext());
	}

	/**
	 * Возвращает идентификатор пользователя в указанном контексте безопасности
	 * 
	 * @param context Контекст безопасности
	 * @return
	 */
	public static Integer getIUserId(SecurityContext context)
	{
		User u = getIUser(context);
		return u == null ? null : u.getId();
	}

	/**
	 * Возвращает идентификатор текущего пользователя (null, если пользователь не авторизован)
	 * 
	 * @return
	 */
	public static Integer getCurrentIUserId()
	{
		return getIUserId(SecurityContextHolder.getContext());
	}

	/**
	 * Обновляет в сессии данные текущего пользователя. Метод вызывается после изменения данных в профиле.
	 * 
	 * @param newUserInfo Новые данные пользователя
	 */
	public static void refreshCurrentUserInfo(User newUserInfo)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			logger.debug("Authentication is null!");
		else
		{
			Object principal = auth.getPrincipal();
			if (principal instanceof TUserDetails)
				((TUserDetails) principal).setIUser(newUserInfo);
		}
	}

	/**
	 * Проверяет, является ли текущий пользователь администратором
	 * 
	 * @return
	 */
	public static boolean isAdmin()
	{
		return false;
		// User u = getCurrentIUser();
		// return u != null && u.isAdmin();
	}

	public static void activateSession(User iuser)
	{
		TUserDetails details = new TUserDetails();
		details.setIUser(iuser);
		FastAuthToken authToken = new FastAuthToken(details);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@SuppressWarnings("serial")
	private static class FastAuthToken extends AbstractAuthenticationToken
	{
		private TUserDetails details;

		public FastAuthToken(TUserDetails details)
		{
			super(details.getAuthorities());
			this.details = details;
			setAuthenticated(true);
		}

		@Override
		public Object getCredentials()
		{
			return null;
		}

		@Override
		public Object getPrincipal()
		{
			return details;
		}
	}
}
