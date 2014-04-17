package ru.terra.spending.engine;

import org.apache.log4j.Logger;
import ru.terra.server.engine.AbstractEngine;
import ru.terra.spending.db.controller.TransactionJpaController;
import ru.terra.spending.db.entity.Transaction;
import ru.terra.spending.dto.TransactionDTO;

import java.util.Date;

/**
 * Date: 16.04.14
 * Time: 15:38
 */
public class TransactionsEngine extends AbstractEngine<Transaction, TransactionDTO> {
    private Logger logger = Logger.getLogger(this.getClass());
    private TypeEngine typeEngine = new TypeEngine();
    private UsersEngine usersEngine = new UsersEngine();

    public TransactionsEngine() {
        super(new TransactionJpaController());
    }

    @Override
    public TransactionDTO getDto(Integer id) {
        return entityToDto(getBean(id));
    }

    @Override
    public void dtoToEntity(TransactionDTO dto, Transaction transaction) {
        transaction.setCreateDate(new Date());
        transaction.setTrDate(new Date(dto.date));
        transaction.setType(typeEngine.getBean(dto.type));
        try {
            transaction.setUser(usersEngine.getUser(dto.user.id));
        } catch (Exception e) {
            logger.error("Unable to set user", e);
        }
        transaction.setValue(dto.value);
    }

    @Override
    public TransactionDTO entityToDto(Transaction transaction) {
        return new TransactionDTO(transaction);
    }
}
