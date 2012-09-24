/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.spending.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author terranz
 */
@Entity
@Table(name = "reports", catalog = "spending", schema = "", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
		@NamedQuery(name = "Report.findById", query = "SELECT r FROM Report r WHERE r.id = :id"),
		@NamedQuery(name = "Report.findByName", query = "SELECT r FROM Report r WHERE r.name = :name"),
		@NamedQuery(name = "Report.findByQuery", query = "SELECT r FROM Report r WHERE r.query = :query") })
public class Report implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 128)
	private String name;
	@Basic(optional = false)
	@Column(name = "query", nullable = false, length = 512)
	private String query;

	public Report()
	{
	}

	public Report(Integer id)
	{
		this.id = id;
	}

	public Report(Integer id, String name, String query)
	{
		this.id = id;
		this.name = name;
		this.query = query;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Report))
		{
			return false;
		}
		Report other = (Report) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "ru.terra.spending.db.entity.Report[ id=" + id + " ]";
	}

}
