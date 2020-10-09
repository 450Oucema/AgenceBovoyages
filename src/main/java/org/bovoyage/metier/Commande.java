package org.bovoyage.metier;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "commandes")
public class Commande
{
    @Id
    protected String id;

    @ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "idUser")
    protected User user;

    protected boolean payee;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "commande")
    private Set<Item> items = new HashSet<Item>();

    public Commande() {
    }

    public Commande(String id, User user, boolean payee) {
        this.id = id;
        this.user = user;
        this.payee = payee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPayee() {
        return payee;
    }

    public void setPayee(boolean payee) {
        this.payee = payee;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
