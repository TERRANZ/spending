package ru.terra.spending.db.controller;

import ru.terra.server.db.controllers.AbstractJpaController;
import ru.terra.spending.db.controller.exceptions.NonexistentEntityException;
import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.db.entity.Transaction;
import ru.terra.spending.db.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author terranz
 */
public class TransactionJpaController extends AbstractJpaController<Transaction> {

    public TransactionJpaController() {
        super(Transaction.class);
    }

    public void create(Transaction transaction) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TrType typeId = transaction.getTypeId();
            if (typeId != null) {
                typeId = em.getReference(typeId.getClass(), typeId.getId());
                transaction.setType(typeId);
            }
            User userId = transaction.getUser();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                transaction.setUser(userId);
            }
            em.persist(transaction);
            if (typeId != null) {
                typeId.getTransactionList().add(transaction);
                typeId = em.merge(typeId);
            }
            if (userId != null) {
                userId.getTransactionList().add(transaction);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaction transaction;
            try {
                transaction = em.getReference(Transaction.class, id);
                transaction.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.", enfe);
            }
            TrType typeId = transaction.getTypeId();
            if (typeId != null) {
                typeId.getTransactionList().remove(transaction);
                typeId = em.merge(typeId);
            }
            User userId = transaction.getUser();
            if (userId != null) {
                userId.getTransactionList().remove(transaction);
                userId = em.merge(userId);
            }
            em.remove(transaction);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void update(Transaction transaction) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaction persistentTransaction = em.find(Transaction.class, transaction.getId());
            TrType typeIdOld = persistentTransaction.getTypeId();
            TrType typeIdNew = transaction.getTypeId();
            User userIdOld = persistentTransaction.getUser();
            User userIdNew = transaction.getUser();
            if (typeIdNew != null) {
                typeIdNew = em.getReference(typeIdNew.getClass(), typeIdNew.getId());
                transaction.setType(typeIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                transaction.setUser(userIdNew);
            }
            transaction = em.merge(transaction);
            if (typeIdOld != null && !typeIdOld.equals(typeIdNew)) {
                typeIdOld.getTransactionList().remove(transaction);
                typeIdOld = em.merge(typeIdOld);
            }
            if (typeIdNew != null && !typeIdNew.equals(typeIdOld)) {
                typeIdNew.getTransactionList().add(transaction);
                typeIdNew = em.merge(typeIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getTransactionList().remove(transaction);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getTransactionList().add(transaction);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaction.getId();
                if (get(id) == null) {
                    throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaction> findTransactionEntities(User user) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Transaction.findByUser").setParameter("uid", user);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
