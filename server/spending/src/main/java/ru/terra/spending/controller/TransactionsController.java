package ru.terra.spending.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TransactionsController
{
	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String transactions(Locale locale, Model model)
	{
		return "transactions";
	}
}
