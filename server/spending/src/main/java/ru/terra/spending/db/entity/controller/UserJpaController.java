/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.spending.db.entity.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.terra.spending.db.entity.Transaction;
import ru.terra.spending.db.entity.User;
import ru.terra.spending.db.entity.controller.exceptions.IllegalOrphanException;
import ru.terra.spending.db.entity.controller.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class UserJpaController implements Serializable
{

	public UserJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(User user)
	{
		if (user.getTransactionList() == null)
		{
			user.setTransactionList(new ArrayList<Transaction>());
		}
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
			for (Transaction transactionListTransactionToAttach : user.getTransactionList())
			{
				transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(),
						transactionListTransactionToAttach.getId());
				attachedTransactionList.add(transactionListTransactionToAttach);
			}
			user.setTransactionList(attachedTransactionList);
			em.persist(user);
			for (Transaction transactionListTransaction : user.getTransactionList())
			{
				User oldUserIdOfTransactionListTransaction = transactionListTransaction.getUserId();
				transactionListTransaction.setUserId(user);
				transactionListTransaction = em.merge(transactionListTransaction);
				if (oldUserIdOfTransactionListTransaction != null)
				{
					oldUserIdOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
					oldUserIdOfTransactionListTransaction = em.merge(oldUserIdOfTransactionListTransaction);
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

	public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			User persistentUser = em.find(User.class, user.getId());
			List<Transaction> transactionListOld = persistentUser.getTransactionList();
			List<Transaction> transactionListNew = user.getTransactionList();
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
							+ " since its userId field is not nullable.");
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
			user.setTransactionList(transactionListNew);
			user = em.merge(user);
			for (Transaction transactionListNewTransaction : transactionListNew)
			{
				if (!transactionListOld.contains(transactionListNewTransaction))
				{
					User oldUserIdOfTransactionListNewTransaction = transactionListNewTransaction.getUserId();
					transactionListNewTransaction.setUserId(user);
					transactionListNewTransaction = em.merge(transactionListNewTransaction);
					if (oldUserIdOfTransactionListNewTransaction != null && !oldUserIdOfTransactionListNewTransaction.equals(user))
					{
						oldUserIdOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
						oldUserIdOfTransactionListNewTransaction = em.merge(oldUserIdOfTransactionListNewTransaction);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = user.getId();
				if (findUser(id) == null)
				{
					throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
			User user;
			try
			{
				user = em.getReference(User.class, id);
				user.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Transaction> transactionListOrphanCheck = user.getTransactionList();
			for (Transaction transactionListOrphanCheckTransaction : transactionListOrphanCheck)
			{
				if (illegalOrphanMessages == null)
				{
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Transaction "
						+ transactionListOrphanCheckTransaction + " in its transactionList field has a non-nullable userId field.");
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(user);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<User> findUserEntities()
	{
		return findUserEntities(true, -1, -1);
	}

	public List<User> findUserEntities(int maxResults, int firstResult)
	{
		return findUserEntities(false, maxResults, firstResult);
	}

	private List<User> findUserEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(User.class));
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

	public User findUser(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(User.class, id);
		} finally
		{
			em.close();
		}
	}

	public User findUser(String login)
	{
		EntityManager em = getEntityManager();
		try
		{
			return (User) em.createNamedQuery("User.findByLogin").setParameter("login", login).getSingleResult();
		} finally
		{
			em.close();
		}
	}

	public User findUser(String login, String password)
	{
		EntityManager em = getEntityManager();
		try
		{
			return (User) em.createNamedQuery("User.findByLoginAndPassword").setParameter("login", login).setParameter("password", password)
					.getSingleResult();
		} finally
		{
			em.close();
		}
	}

	public int getUserCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<User> rt = cq.from(User.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

}
