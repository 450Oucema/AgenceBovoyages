package org.bovoyage.metier;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "commande_item")
public class Item
{
    @Id
    @Column(name = "id")
    protected String id;

    @ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "commande")
    protected Commande commande;

    @ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "sejour")
    protected Sejour sejour;

    @Column(name = "nbpersonnes")
    protected int nbPersonnes;

    public Item() {
        this.id = UUID.randomUUID().toString();
    }

    public Item(String id, Commande commande, Sejour sejour, int nbPersonnes) {
        this.id = id;
        this.commande = commande;
        this.sejour = sejour;
        this.nbPersonnes = nbPersonnes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Sejour getSejour() {
        return sejour;
    }

    public void setSejour(Sejour sejour) {
        this.sejour = sejour;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }
}
