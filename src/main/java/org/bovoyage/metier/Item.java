package org.bovoyage.metier;


import javax.persistence.*;

@Entity
@Table(name = "commander")
public class Item
{
    @Id
    @Column(name = "idCommander")
    protected String id;

    @ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idCommande")
    protected Commande commande;

    @ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idVoyage")
    protected Sejour sejour;

    protected int nbPersonnes;

    public Item() {
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
