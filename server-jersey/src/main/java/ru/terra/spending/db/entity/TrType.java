/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.spending.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author terranz
 */
@Entity
@Table(name = "tr_types", catalog = "spending", schema = "", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "TrType.findAll", query = "SELECT t FROM TrType t"),
		@NamedQuery(name = "TrType.findById", query = "SELECT t FROM TrType t WHERE t.id = :id"),
		@NamedQuery(name = "TrType.findByName", query = "SELECT t FROM TrType t WHERE t.name = :name") })
public class TrType implements Serializable
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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "typeId")
	private List<Transaction> transactionList;

	public TrType()
	{
	}

	public TrType(Integer id)
	{
		this.id = id;
	}

	public TrType(Integer id, String name)
	{
		this.id = id;
		this.name = name;
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

	@XmlTransient
	public List<Transaction> getTransactionList()
	{
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList)
	{
		this.transactionList = transactionList;
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
		if (!(object instanceof TrType))
		{
			return false;
		}
		TrType other = (TrType) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "ru.terra.spending.db.entity.TrType[ id=" + id + " ]";
	}

}
