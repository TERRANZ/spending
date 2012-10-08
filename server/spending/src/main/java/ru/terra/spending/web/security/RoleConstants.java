package ru.terra.spending.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class RoleConstants
{

	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	public static final String ROLE_USER = "ROLE_USER";

	public static final GrantedAuthority GA_ANONYMOUS = new GrantedAuthorityImpl("ROLE_ANONYMOUS");
	public static final GrantedAuthority GA_USER = new GrantedAuthorityImpl("ROLE_USER");

}
