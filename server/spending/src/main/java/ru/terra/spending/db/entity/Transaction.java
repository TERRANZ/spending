/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.spending.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author terranz
 */
@Entity
@Table(name = "transactions", catalog = "spending", schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
		@NamedQuery(name = "Transaction.findById", query = "SELECT t FROM Transaction t WHERE t.id = :id"),
		@NamedQuery(name = "Transaction.findByValue", query = "SELECT t FROM Transaction t WHERE t.value = :value"),
		@NamedQuery(name = "Transaction.findByTrDate", query = "SELECT t FROM Transaction t WHERE t.trDate = :trDate"),
		@NamedQuery(name = "Transaction.findByCreateDate", query = "SELECT t FROM Transaction t WHERE t.createDate = :createDate") })
public class Transaction implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "value", nullable = false)
	private double value;
	@Column(name = "tr_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date trDate;
	@Basic(optional = false)
	@Column(name = "create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private TrType typeId;
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private User userId;

	public Transaction()
	{
	}

	public Transaction(Integer id)
	{
		this.id = id;
	}

	public Transaction(Integer id, double value, Date createDate)
	{
		this.id = id;
		this.value = value;
		this.createDate = createDate;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public Date getTrDate()
	{
		return trDate;
	}

	public void setTrDate(Date trDate)
	{
		this.trDate = trDate;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public TrType getTypeId()
	{
		return typeId;
	}

	public void setTypeId(TrType typeId)
	{
		this.typeId = typeId;
	}

	public User getUserId()
	{
		return userId;
	}

	public void setUserId(User userId)
	{
		this.userId = userId;
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
		if (!(object instanceof Transaction))
		{
			return false;
		}
		Transaction other = (Transaction) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "ru.terra.spending.db.entity.Transaction[ id=" + id + " ]";
	}

}
