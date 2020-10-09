package org.bovoyage.repositories;
import org.bovoyage.metier.Image;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ImageRepository
{
    private static EntityManagerFactory emf;
    private EntityManager em = null;
    private static ImageRepository instance = null;

    public static ImageRepository getInstance()
    {
        if (instance == null)
            try {
                instance = new ImageRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return instance;
    }

    private ImageRepository() throws Exception
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

    public List<Image> findAll()
    {
        List<Image> images = new ArrayList<Image>();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<Image> query = em.createQuery("SELECT i FROM Image i", Image.class);
            images = (List<Image>) query.getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return images;
    }

    public List<Image> findByDestination(String id)
    {
        List<Image> images = new ArrayList<Image>();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<Image> query = em.createQuery("SELECT i FROM Image i", Image.class);
            images = (List<Image>) query.getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return images;
    }
}
