package ru.terra.spending.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.web.security.SessionHelper;

@Controller
public class TransactionsController
{
	private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

	@RequestMapping(value = URLConstants.Pages.TRANSACTIONS, method = RequestMethod.GET)
	public String transactions(Locale locale, Model model)
	{
		User u = SessionHelper.getCurrentIUser();
		if (u == null)
		{
			logger.info("User in session is null");
		}
		else
		{
			model.addAttribute("user", u);
		}
		return URLConstants.Views.TRANSACTIONS;
	}

	@RequestMapping(value = URLConstants.Pages.TRANSACTIONS_ADD, method = RequestMethod.GET)
	public String transactionsAdd(Locale locale, Model model)
	{
		return URLConstants.Views.TRANSACTIONS_ADD;
	}
	
	@RequestMapping(value = URLConstants.Pages.TRANSACTIONS_VIEW, method = RequestMethod.GET)
	public String transactionsView(Locale locale, Model model)
	{
		return URLConstants.Views.TRANSACTIONS_VIEW;
	}
}
