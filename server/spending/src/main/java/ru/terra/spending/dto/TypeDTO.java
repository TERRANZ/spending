package ru.terra.spending.dto;

import ru.terra.spending.db.entity.TrType;

public class TypeDTO
{
	public Integer id;
	public String name;

	public TypeDTO(TrType tt)
	{
		this.id = tt.getId();
		this.name = tt.getName();
	}
}
