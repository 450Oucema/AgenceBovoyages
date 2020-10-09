package org.bovoyage.panier;

import org.bovoyage.metier.Voyage;

import java.util.ArrayList;

public class Panier
{
    protected ArrayList<Voyage> voyages;
    protected int items;

    public Panier() {
        this.voyages = new ArrayList<Voyage>();
        this.items = 0;
    }

    public ArrayList<Voyage> getVoyages() {
        return voyages;
    }

    public void setVoyages(ArrayList<Voyage> voyages) {
        this.voyages = voyages;
    }

    public void addVoyage(Voyage voyage) {
        this.voyages.add(voyage);
    }

    public int getItems() {
        return this.items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public Voyage findVoyage(String idSejour) {
        Voyage v = this.voyages.stream().filter(
                voyage -> voyage.getSejour().getId() == Integer.parseInt(idSejour)
        ).findAny().orElse(null);

        return v;
    }
}
