package ru.terra.spending.engine;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.db.entity.Transaction;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.db.entity.controller.TransactionJpaController;
import ru.terra.spending.dto.TransactionDTO;

@Singleton
@Component
public class TransactionEngine
{
	private TransactionJpaController tjcp;

	public TransactionEngine()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("spending-dbPU");
		tjcp = new TransactionJpaController(emf);
	}

	public Integer registerTransaction(User userId, TrType type, Double money, Long date)
	{
		Transaction tr = new Transaction();
		tr.setTrDate(new Date(date));
		tr.setTypeId(type);
		tr.setUserId(userId);
		tr.setValue(money);
		tjcp.create(tr);
		return tr.getId();
	}

	public List<TransactionDTO> getTransactions(Long uid)
	{
		List<TransactionDTO> ret = new LinkedList<TransactionDTO>();
		for (Transaction t : tjcp.findTransactionEntities(uid))
		{
			ret.add(new TransactionDTO(t));
		}
		return ret;
	}
}
