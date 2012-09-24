package ru.terra.spending.engine;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.spending.db.entity.controller.ReportJpaController;

@Singleton
@Component
public class ReportEngine
{
	private ReportJpaController rjcp;

	public ReportEngine()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("spending-dbPU");
		rjcp = new ReportJpaController(emf);
	}

	public String getReport(Integer reportId)
	{
		return rjcp.findReport(reportId).getQuery();
	}

	public String getReport(String reportName)
	{
		return rjcp.findReport(reportName).getQuery();
	}
}
