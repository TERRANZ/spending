package ru.terra.spending.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.spending.ResponceUtils;
import ru.terra.spending.constants.URLConstants;
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

	@RequestMapping(value = URLConstants.DoJson.MobileTransactions.MT_GET_TR.URL, method = RequestMethod.GET)
	public ResponseEntity<String> getTransactios(HttpServletRequest request)
	{
		String json = "";
		String uid = request.getParameter(URLConstants.DoJson.MobileTransactions.MT_GET_TR.PARAM_USER);
		if (uid != null)
		{
			Integer luid = -1;
			try
			{
				luid = Integer.parseInt(uid);
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
		String uid = request.getParameter(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_UID);
		String type = request.getParameter(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_TYPE);
		String money = request.getParameter(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_MONEY);
		String date = request.getParameter(URLConstants.DoJson.MobileTransactions.MT_REG_TR.PARAM_DATE);
		Integer id = te.registerTransaction(ue.getUser(Integer.parseInt(uid)), tte.getType(Integer.parseInt(type)), Double.parseDouble(money),
				Long.parseLong(date));
		json = new JSONSerializer().deepSerialize(new OperationResultDTO("ok", id));
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.DoJson.MobileTransactions.MT_REG_TR.URL, method = RequestMethod.GET)
	public ResponseEntity<String> regTransactiosGet(HttpServletRequest request)
	{
		return regTransactios(request);
	}

	@RequestMapping(value = URLConstants.DoJson.MobileTransactions.MT_REG_TR.URL, method = RequestMethod.POST)
	public ResponseEntity<String> regTransactiosPost(HttpServletRequest request)
	{
		return regTransactios(request);
	}
}
