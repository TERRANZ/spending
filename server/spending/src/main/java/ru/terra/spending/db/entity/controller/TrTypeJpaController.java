/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.spending.db.entity.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.terra.spending.db.entity.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.db.entity.controller.exceptions.IllegalOrphanException;
import ru.terra.spending.db.entity.controller.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class TrTypeJpaController implements Serializable
{

	public TrTypeJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(TrType trType)
	{
		if (trType.getTransactionList() == null)
		{
			trType.setTransactionList(new ArrayList<Transaction>());
		}
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
			for (Transaction transactionListTransactionToAttach : trType.getTransactionList())
			{
				transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(),
						transactionListTransactionToAttach.getId());
				attachedTransactionList.add(transactionListTransactionToAttach);
			}
			trType.setTransactionList(attachedTransactionList);
			em.persist(trType);
			for (Transaction transactionListTransaction : trType.getTransactionList())
			{
				TrType oldTypeIdOfTransactionListTransaction = transactionListTransaction.getTypeId();
				transactionListTransaction.setTypeId(trType);
				transactionListTransaction = em.merge(transactionListTransaction);
				if (oldTypeIdOfTransactionListTransaction != null)
				{
					oldTypeIdOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
					oldTypeIdOfTransactionListTransaction = em.merge(oldTypeIdOfTransactionListTransaction);
				}
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

	public void edit(TrType trType) throws IllegalOrphanException, NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			TrType persistentTrType = em.find(TrType.class, trType.getId());
			List<Transaction> transactionListOld = persistentTrType.getTransactionList();
			List<Transaction> transactionListNew = trType.getTransactionList();
			List<String> illegalOrphanMessages = null;
			for (Transaction transactionListOldTransaction : transactionListOld)
			{
				if (!transactionListNew.contains(transactionListOldTransaction))
				{
					if (illegalOrphanMessages == null)
					{
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Transaction " + transactionListOldTransaction
							+ " since its typeId field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
			for (Transaction transactionListNewTransactionToAttach : transactionListNew)
			{
				transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(),
						transactionListNewTransactionToAttach.getId());
				attachedTransactionListNew.add(transactionListNewTransactionToAttach);
			}
			transactionListNew = attachedTransactionListNew;
			trType.setTransactionList(transactionListNew);
			trType = em.merge(trType);
			for (Transaction transactionListNewTransaction : transactionListNew)
			{
				if (!transactionListOld.contains(transactionListNewTransaction))
				{
					TrType oldTypeIdOfTransactionListNewTransaction = transactionListNewTransaction.getTypeId();
					transactionListNewTransaction.setTypeId(trType);
					transactionListNewTransaction = em.merge(transactionListNewTransaction);
					if (oldTypeIdOfTransactionListNewTransaction != null && !oldTypeIdOfTransactionListNewTransaction.equals(trType))
					{
						oldTypeIdOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
						oldTypeIdOfTransactionListNewTransaction = em.merge(oldTypeIdOfTransactionListNewTransaction);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = trType.getId();
				if (findTrType(id) == null)
				{
					throw new NonexistentEntityException("The trType with id " + id + " no longer exists.");
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

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			TrType trType;
			try
			{
				trType = em.getReference(TrType.class, id);
				trType.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The trType with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Transaction> transactionListOrphanCheck = trType.getTransactionList();
			for (Transaction transactionListOrphanCheckTransaction : transactionListOrphanCheck)
			{
				if (illegalOrphanMessages == null)
				{
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This TrType (" + trType + ") cannot be destroyed since the Transaction "
						+ transactionListOrphanCheckTransaction + " in its transactionList field has a non-nullable typeId field.");
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(trType);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<TrType> findTrTypeEntities()
	{
		return findTrTypeEntities(true, -1, -1);
	}

	public List<TrType> findTrTypeEntities(int maxResults, int firstResult)
	{
		return findTrTypeEntities(false, maxResults, firstResult);
	}

	private List<TrType> findTrTypeEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(TrType.class));
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

	public TrType findTrType(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(TrType.class, id);
		} finally
		{
			em.close();
		}
	}

	public int getTrTypeCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<TrType> rt = cq.from(TrType.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

}
