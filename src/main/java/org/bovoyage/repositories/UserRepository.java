package org.bovoyage.repositories;

import org.bovoyage.metier.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository
{
    private static EntityManagerFactory emf;
    private EntityManager em = null;
    private static UserRepository instance = null;

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
}
