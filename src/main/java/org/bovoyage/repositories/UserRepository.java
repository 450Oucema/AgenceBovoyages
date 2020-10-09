package org.bovoyage.repositories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bovoyage.dao.UserDAO;
import org.bovoyage.metier.Destination;
import org.bovoyage.metier.User;
import org.bovoyage.security.Password;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class UserRepository
{
    private static EntityManagerFactory emf;
    private EntityManager em = null;
    private static UserRepository instance = null;
    private Log log = LogFactory.getLog(UserRepository.class);


    public static UserRepository getInstance()
    {
        if (instance == null)
            try {
                instance = new UserRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return instance;
    }

    private UserRepository() throws Exception
    {
        try {
            emf = Persistence.createEntityManagerFactory("pu");
            em = emf.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Datasource not found : " + ex.getMessage());
        }
    }

    public List<User> findAll()
    {
        List<User> users = new ArrayList<User>();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            users = (List<User>) query.getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return users;
    }

    public boolean register(User user)
    {
        boolean isRegistered = false;
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            em.persist(user);
            tx.commit();
            isRegistered = true;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        em.close();

        return isRegistered;
    }

    public User authenticate(String email, String password)
    {
        User user = new User();
        log.debug("test" + user.toString());
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", Password.passwordHash(password));
            user = (User) query.getSingleResult();
            log.debug(user.toString());
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return user;
    }
}
