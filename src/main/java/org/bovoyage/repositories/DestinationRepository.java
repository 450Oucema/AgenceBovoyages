package org.bovoyage.repositories;

import org.bovoyage.metier.Destination;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DestinationRepository
{
    private static EntityManagerFactory emf;
    private EntityManager em = null;
    private static DestinationRepository instance = null;

    public static DestinationRepository getInstance()
    {
        if (instance == null)
            try {
                instance = new DestinationRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return instance;
    }

    private DestinationRepository() throws Exception
    {
        try {
            emf = Persistence.createEntityManagerFactory("pu");
            System.out.println("*****   emf ok");
            em = emf.createEntityManager();
            System.out.println("*****   em  ok");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Datasource not found : " + ex.getMessage());
        }
    }

    public List<Destination> findAll()
    {
        List<Destination> destinations = new ArrayList<Destination>();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<Destination> query = em.createQuery("SELECT d FROM Destination d", Destination.class);
            destinations  = (List<Destination>) query.getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return destinations;
    }

    public Destination find(int id)
    {
        Destination destination = new Destination();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            TypedQuery<Destination> query = em.createQuery("SELECT d FROM Destination d WHERE d.identifiant = :id", Destination.class);
            query.setParameter("id", id);
            destination  = (Destination) query.getSingleResult();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return destination;
    }
}
