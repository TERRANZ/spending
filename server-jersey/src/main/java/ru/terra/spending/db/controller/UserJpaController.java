package ru.terra.spending.db.controller;

import ru.terra.server.db.controllers.AbstractJpaController;
import ru.terra.spending.db.controller.exceptions.NonexistentEntityException;
import ru.terra.spending.db.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;

/**
 * @author terranz
 */
public class UserJpaController extends AbstractJpaController<User> implements Serializable {

    public UserJpaController() {
        super(User.class);
    }

    public void create(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {

        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {

        }
    }

    @Override
    public void update(User user) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (get(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {

        }
    }

    public User findUser(String login) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByLogin").setParameter("login", login);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {

        }
    }

    public User findUser(String login, String password) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByLoginAndPassword").setParameter("login", login).setParameter("password", password);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {

        }
    }

}
