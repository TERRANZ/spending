package ru.terra.spending.dto;

public class OperationResultDTO
{
	public String message = "";
	public Integer retid = 0;

	public OperationResultDTO()
	{
	}

	public OperationResultDTO(String message, Integer retid)
	{
		super();
		this.message = message;
		this.retid = retid;
	}
}
