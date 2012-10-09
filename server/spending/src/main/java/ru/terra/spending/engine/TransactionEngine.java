package ru.terra.spending.engine;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.terra.spending.HomeController;
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
	
	private UsersEngine ue;
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionEngine.class);

	public TransactionEngine()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("spending-dbPU");
		ue = new UsersEngine();
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

	public List<TransactionDTO> getTransactions(Integer uid)
	{
		List<TransactionDTO> ret = new LinkedList<TransactionDTO>();
		logger.info("getTransactions for user: " + uid);
		for (Transaction t : tjcp.findTransactionEntities(ue.getUser(uid)))
		{
			ret.add(new TransactionDTO(t));
		}
		return ret;
	}
}
