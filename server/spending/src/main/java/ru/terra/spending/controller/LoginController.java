package ru.terra.spending.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.spending.ResponceUtils;
import ru.terra.spending.dto.LoginDTO;
import ru.terra.spending.engine.UsersEngine;
import flexjson.JSONSerializer;

@Controller
public class LoginController
{

	@Inject
	private UsersEngine le;

	@RequestMapping(value = "/login/do.login.json", method = RequestMethod.GET)
	public ResponseEntity<String> mobileLogin(HttpServletRequest request)
	{
		LoginDTO ret = new LoginDTO();
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		if (le.login(login, pass))
		{
			ret.logged = true;
		}
		else
		{
			ret.logged = false;
		}
		String json = new JSONSerializer().serialize(ret);
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = "/login/do.register.json", method = RequestMethod.GET)
	public ResponseEntity<String> register(HttpServletRequest request)
	{
		LoginDTO ret = new LoginDTO();
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		if (login != null && pass != null)
		{
			Integer retId = le.registerUser(login, pass);
			ret.logged = true;
			ret.id = retId;
		}
		else
		{
			ret.logged = false;
		}
		String json = new JSONSerializer().serialize(ret);
		return ResponceUtils.makeResponce(json);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model)
	{
		return "transactions";
	}
}
