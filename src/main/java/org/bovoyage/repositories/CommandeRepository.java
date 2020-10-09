package org.bovoyage.repositories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bovoyage.metier.Commande;
import org.bovoyage.metier.Item;
import org.bovoyage.metier.User;
import org.bovoyage.metier.Voyage;
import org.bovoyage.panier.Panier;
import org.bovoyage.security.Password;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandeRepository
{
    private static EntityManagerFactory emf;
    private EntityManager em = null;
    private static CommandeRepository instance = null;
    private Log log = LogFactory.getLog(CommandeRepository.class);


    public static CommandeRepository getInstance()
    {
        if (instance == null)
            try {
                instance = new CommandeRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return instance;
    }

    private CommandeRepository() throws Exception
    {
        try {
            emf = Persistence.createEntityManagerFactory("pu");
            em = emf.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Datasource not found : " + ex.getMessage());
        }
    }

    public void order(Panier panier, User user)
    {
        Set<Item> items = new HashSet<Item>();
        Commande commande = new Commande();
        commande.setUser(user);

        log.info(user.toString());

        for(Voyage voyage: panier.getVoyages()) {
            Item item = new Item();
            item.setCommande(commande);
            item.setNbPersonnes(voyage.getNbPersonnes());
            item.setSejour(voyage.getSejour());
            items.add(item);
        }

        commande.setItems(items);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.persist(commande);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

    }
}
