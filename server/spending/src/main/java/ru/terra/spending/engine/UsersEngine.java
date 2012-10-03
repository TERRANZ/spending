package ru.terra.spending.engine;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.spending.db.entity.User;
import ru.terra.spending.db.entity.controller.UserJpaController;

@Singleton
@Component
public class UsersEngine
{
	private UserJpaController ujpc;

	public UsersEngine()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("spending-dbPU");
		ujpc = new UserJpaController(emf);
	}

	public Integer registerUser(String login, String password)
	{
		if (ujpc.findUser(login) != null)
		{
			return -1;
		}
		else
		{
			User u = new User();
			u.setLogin(login);
			u.setPassword(password);
			ujpc.create(u);
			return u.getId();
		}
	}

	public Integer getUserId(String login)
	{
		User u = ujpc.findUser(login);
		return u != null ? u.getId() : -1;
	}

	public boolean login(String login, String password)
	{
		User u = ujpc.findUser(login, password);
		return u != null;
	}

	public User getUser(Integer uid)
	{
		return ujpc.findUser(uid);
	}

	public User findUserByName(String name)
	{
		return ujpc.findUser(name);
	}
}
