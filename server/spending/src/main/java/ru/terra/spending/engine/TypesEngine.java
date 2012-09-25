package ru.terra.spending.engine;

import javax.inject.Singleton;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.db.entity.controller.TrTypeJpaController;

@Singleton
@Component
public class TypesEngine
{
	private TrTypeJpaController ttjcp = new TrTypeJpaController(Persistence.createEntityManagerFactory("spending-dbPU"));

	public TypesEngine()
	{
	}

	public TrType getType(Integer id)
	{
		return ttjcp.findTrType(id);
	}
}
