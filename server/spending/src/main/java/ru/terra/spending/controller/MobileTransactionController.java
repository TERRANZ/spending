package ru.terra.spending.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.spending.dto.LoginDto;

import flexjson.JSONSerializer;


@Controller
public class MobileTransactionController
{
	@RequestMapping(value = "/spending/do.login.json", method = RequestMethod.GET)
	public ResponseEntity<String> mobileLogin(HttpServletRequest request)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String json = new JSONSerializer().serialize(new LoginDto());
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
}
