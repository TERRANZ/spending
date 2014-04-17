package ru.terra.spending.controller;

import ru.terra.server.controller.AbstractController;
import ru.terra.spending.constants.URLConstants;
import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.dto.TypeDTO;
import ru.terra.spending.engine.TypeEngine;

import javax.ws.rs.Path;

@Path(URLConstants.Types.TYPES)
public class TypesController extends AbstractController<TrType, TypeDTO, TypeEngine> {
    public TypesController() {
        super(TypeEngine.class, false);
    }
}
