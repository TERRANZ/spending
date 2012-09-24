/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.spending.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author terranz
 */
@Entity
@Table(name = "users", catalog = "spending", schema = "", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
		@NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
		@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
		@NamedQuery(name = "User.findByLevel", query = "SELECT u FROM User u WHERE u.level = :level"),
		@NamedQuery(name = "User.findByLoginAndPassword", query = "SELECT u FROM User u where u.login = :login and u.password = :password") })
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "login", nullable = false, length = 128)
	private String login;
	@Basic(optional = false)
	@Column(name = "password", nullable = false, length = 128)
	private String password;
	@Column(name = "level")
	private Integer level;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	private List<Transaction> transactionList;

	public User()
	{
	}

	public User(Integer id)
	{
		this.id = id;
	}

	public User(Integer id, String login, String password)
	{
		this.id = id;
		this.login = login;
		this.password = password;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
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
		if (!(object instanceof User))
		{
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "ru.terra.spending.db.entity.User[ id=" + id + " ]";
	}

}
