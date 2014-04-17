package ru.terra.spending.engine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.spending.db.controller.UserJpaController;
import ru.terra.spending.db.controller.exceptions.IllegalOrphanException;
import ru.terra.spending.db.controller.exceptions.NonexistentEntityException;
import ru.terra.spending.db.entity.User;

import javax.persistence.NoResultException;


public class UsersEngine {
    private static final Logger logger = LoggerFactory.getLogger(UsersEngine.class);
    private UserJpaController ujpc = new UserJpaController();

    public Integer registerUser(String login, String password) {
        if (ujpc.findUser(login) != null) {
            return -1;
        } else {
            User u = new User();
            u.setLogin(login);
            u.setPassword(password);
            u.setLevel(0);
            ujpc.create(u);
            return u.getId();
        }
    }

    public Integer getUserId(String login) {
        User u = ujpc.findUser(login);
        return u != null ? u.getId() : -1;
    }

    public User login(String login, String password) {
        User u = ujpc.findUser(login, password);
        return u;
    }

    public User getUser(Integer uid) throws Exception {
        return ujpc.get(uid);
    }

    public User findUserByName(String name) {
        logger.info("findUserByName " + name);
        User u = null;
        try {
            u = ujpc.findUser(name);
        } catch (NoResultException e) {
            logger.info("error while loading user " + e.getMessage());
            e.printStackTrace();
        }
        return u;
    }

    public boolean saveUser(User u) {
        try {
            ujpc.update(u);
        } catch (IllegalOrphanException e) {
            e.printStackTrace();
            return false;
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
