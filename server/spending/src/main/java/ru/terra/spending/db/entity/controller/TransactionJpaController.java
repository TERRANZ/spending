/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.spending.db.entity.controller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.db.entity.Transaction;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.db.entity.controller.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class TransactionJpaController implements Serializable
{

	public TransactionJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Transaction transaction)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			TrType typeId = transaction.getTypeId();
			if (typeId != null)
			{
				typeId = em.getReference(typeId.getClass(), typeId.getId());
				transaction.setTypeId(typeId);
			}
			User userId = transaction.getUserId();
			if (userId != null)
			{
				userId = em.getReference(userId.getClass(), userId.getId());
				transaction.setUserId(userId);
			}
			em.persist(transaction);
			if (typeId != null)
			{
				typeId.getTransactionList().add(transaction);
				typeId = em.merge(typeId);
			}
			if (userId != null)
			{
				userId.getTransactionList().add(transaction);
				userId = em.merge(userId);
			}
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(Transaction transaction) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Transaction persistentTransaction = em.find(Transaction.class, transaction.getId());
			TrType typeIdOld = persistentTransaction.getTypeId();
			TrType typeIdNew = transaction.getTypeId();
			User userIdOld = persistentTransaction.getUserId();
			User userIdNew = transaction.getUserId();
			if (typeIdNew != null)
			{
				typeIdNew = em.getReference(typeIdNew.getClass(), typeIdNew.getId());
				transaction.setTypeId(typeIdNew);
			}
			if (userIdNew != null)
			{
				userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
				transaction.setUserId(userIdNew);
			}
			transaction = em.merge(transaction);
			if (typeIdOld != null && !typeIdOld.equals(typeIdNew))
			{
				typeIdOld.getTransactionList().remove(transaction);
				typeIdOld = em.merge(typeIdOld);
			}
			if (typeIdNew != null && !typeIdNew.equals(typeIdOld))
			{
				typeIdNew.getTransactionList().add(transaction);
				typeIdNew = em.merge(typeIdNew);
			}
			if (userIdOld != null && !userIdOld.equals(userIdNew))
			{
				userIdOld.getTransactionList().remove(transaction);
				userIdOld = em.merge(userIdOld);
			}
			if (userIdNew != null && !userIdNew.equals(userIdOld))
			{
				userIdNew.getTransactionList().add(transaction);
				userIdNew = em.merge(userIdNew);
			}
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = transaction.getId();
				if (findTransaction(id) == null)
				{
					throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Transaction transaction;
			try
			{
				transaction = em.getReference(Transaction.class, id);
				transaction.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.", enfe);
			}
			TrType typeId = transaction.getTypeId();
			if (typeId != null)
			{
				typeId.getTransactionList().remove(transaction);
				typeId = em.merge(typeId);
			}
			User userId = transaction.getUserId();
			if (userId != null)
			{
				userId.getTransactionList().remove(transaction);
				userId = em.merge(userId);
			}
			em.remove(transaction);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<Transaction> findTransactionEntities()
	{
		return findTransactionEntities(true, -1, -1);
	}

	public List<Transaction> findTransactionEntities(int maxResults, int firstResult)
	{
		return findTransactionEntities(false, maxResults, firstResult);
	}

	public List<Transaction> findTransactionEntities(User user)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query q = em.createNamedQuery("Transaction.findByUser").setParameter("uid", user);
			return q.getResultList();
		} finally
		{
			em.close();
		}
	}

	private List<Transaction> findTransactionEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Transaction.class));
			Query q = em.createQuery(cq);
			if (!all)
			{
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally
		{
			em.close();
		}
	}

	public Transaction findTransaction(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Transaction.class, id);
		} finally
		{
			em.close();
		}
	}

	public int getTransactionCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Transaction> rt = cq.from(Transaction.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

}
