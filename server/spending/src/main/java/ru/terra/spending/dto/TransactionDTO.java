package ru.terra.spending.dto;

import ru.terra.spending.db.entity.Transaction;

public class TransactionDTO
{
	public Integer id;
	public Long date;
	public Double value;
	public Integer type;

	public TransactionDTO(Transaction t)
	{
		id = t.getId();
		date = t.getTrDate().getTime();
		value = t.getValue();
		type = t.getTypeId().getId();
	}

}
