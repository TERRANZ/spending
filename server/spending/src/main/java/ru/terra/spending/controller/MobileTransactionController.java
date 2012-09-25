package ru.terra.spending.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.spending.ResponceUtils;
import ru.terra.spending.dto.OperationResultDTO;
import ru.terra.spending.engine.TransactionEngine;
import ru.terra.spending.engine.TypesEngine;
import ru.terra.spending.engine.UsersEngine;
import flexjson.JSONSerializer;

@Controller
public class MobileTransactionController
{
	@Inject
	private TransactionEngine te;
	@Inject
	private UsersEngine ue;
	@Inject
	private TypesEngine tte;

	@RequestMapping(value = "/mobiletransaction/get.transactions.json", method = RequestMethod.GET)
	public ResponseEntity<String> getTransactios(HttpServletRequest request)
	{
		String json = "";
		String uid = request.getParameter("user");
		if (uid != null)
		{
			Long luid = -1L;
			try
			{
				luid = Long.parseLong(uid);
			} catch (NumberFormatException e)
			{
				// error
			}
			json = new JSONSerializer().deepSerialize(te.getTransactions(luid));
		}
		return ResponceUtils.makeResponce(json);
	}

	private ResponseEntity<String> regTransactios(HttpServletRequest request)
	{
		String json = "";
		String uid = request.getParameter("uid");
		String type = request.getParameter("type");
		String money = request.getParameter("money");
		String date = request.getParameter("date");
		Integer id = te.registerTransaction(ue.getUser(Integer.parseInt(uid)), tte.getType(Integer.parseInt(type)), Double.parseDouble(money),
				Long.parseLong(date));
		json = new JSONSerializer().deepSerialize(new OperationResultDTO("ok", id));
		return ResponceUtils.makeResponce(json);		
	}
	
	@RequestMapping(value = "/mobiletransaction/do.transaction.register.json", method = RequestMethod.GET)
	public ResponseEntity<String> regTransactiosGet(HttpServletRequest request)
	{
		return regTransactios(request);
	}
	
	@RequestMapping(value = "/mobiletransaction/do.transaction.register.json", method = RequestMethod.POST)
	public ResponseEntity<String> regTransactiosPost(HttpServletRequest request)
	{
		return regTransactios(request);
	}
}
