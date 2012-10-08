package ru.terra.spending.web.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class UrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
{

	// public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";

	// public UrlAuthenticationFailureHandler()
	// {
	// super();
	// }
	//
	// public UrlAuthenticationFailureHandler(String defaultFailureUrl)
	// {
	// super(defaultFailureUrl);
	// }
	//
	// public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
	// throws IOException, ServletException
	// {
	//
	// String uname = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
	// if (uname != null && uname.length() > 0)
	// {
	// IUser iUser = IUser.findIUserByLoginOrEmail(uname);
	// if (iUser != null)
	// {
	// if (iUser.isBanned())
	// {
	// response.sendRedirect(URLConstants.Pages.USER_BAN.PAGE);
	// }
	// else
	// {
	// FailedAttempHelper fah = FailedAttempHelper.getInstance();
	// fah.addFail(iUser.getName(), iUser.getEmail());
	// super.onAuthenticationFailure(request, response, exception);
	// }
	// }
	// }
	// }

}
