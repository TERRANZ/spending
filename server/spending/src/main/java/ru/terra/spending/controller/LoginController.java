package ru.terra.spending.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import flexjson.JSONSerializer;

import ru.terra.spending.dto.LoginDTO;
import ru.terra.spending.engine.LoginEngine;

@Controller
public class LoginController
{

	@Inject
	private LoginEngine le;

	@RequestMapping(value = "/login/do.login.json", method = RequestMethod.GET)
	public ResponseEntity<String> mobileLogin(HttpServletRequest request)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
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
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/login/do.register.json", method = RequestMethod.GET)
	public ResponseEntity<String> register(HttpServletRequest request)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
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
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
}
