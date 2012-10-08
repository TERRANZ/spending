package ru.terra.spending.controller;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.spending.ResponceUtils;
import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.dto.TypeDTO;
import ru.terra.spending.engine.TypesEngine;
import flexjson.JSONSerializer;

@Controller
public class TypesController
{
	@Inject
	private TypesEngine tte;

	@RequestMapping(value = URLConstants.DoJson.TYPES_GET_TYPES_JSON, method = RequestMethod.GET)
	public ResponseEntity<String> getTypesGet(HttpServletRequest request)
	{
		List<TypeDTO> ret = new LinkedList<TypeDTO>();

		for (TrType t : tte.getTypes())
		{
			ret.add(new TypeDTO(t));
		}
		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}
}
