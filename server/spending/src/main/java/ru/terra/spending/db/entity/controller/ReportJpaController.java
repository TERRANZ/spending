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

import ru.terra.spending.db.entity.Report;
import ru.terra.spending.db.entity.controller.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class ReportJpaController implements Serializable
{

	public ReportJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Report report)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(report);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(Report report) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			report = em.merge(report);
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = report.getId();
				if (findReport(id) == null)
				{
					throw new NonexistentEntityException("The report with id " + id + " no longer exists.");
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
			Report report;
			try
			{
				report = em.getReference(Report.class, id);
				report.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The report with id " + id + " no longer exists.", enfe);
			}
			em.remove(report);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<Report> findReportEntities()
	{
		return findReportEntities(true, -1, -1);
	}

	public List<Report> findReportEntities(int maxResults, int firstResult)
	{
		return findReportEntities(false, maxResults, firstResult);
	}

	private List<Report> findReportEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Report.class));
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

	public Report findReport(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Report.class, id);
		} finally
		{
			em.close();
		}
	}

	public int getReportCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Report> rt = cq.from(Report.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

	public Report findReport(String reportName)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query q = em.createNamedQuery("Report.findByName", Report.class);
			return (Report) q.setParameter("name", reportName).getSingleResult();
		} finally
		{
			em.close();
		}
	}

}
