package org.bovoyage.metier;

import java.io.Serializable;

public class Voyage implements Serializable
{
	private static final long serialVersionUID = 8686349075167232183L;

	private String id;
	private int nbPersonnes;
	private Sejour sejour;
	private String pays;
	public Voyage()
	{}
	
	public Voyage(Sejour sejour,String pays)
	{
		this.sejour = sejour;
		this.pays = pays;
	}
	public Voyage(Sejour sejour,String pays, int nbPersonnes)
	{
		this(sejour,pays);
		this.nbPersonnes=nbPersonnes;
	}
	
	public void addPersonne()
	{
		this.addPersonne(1);
	}
	public void addPersonne(int nb)
	{
		this.nbPersonnes +=nb;
	}
	public void addPersonne(String nb)
	{
		this.nbPersonnes +=Integer.parseInt(nb);
	}
	public String toString()
	{
		return "Voyage vers "+this.pays+" pour "+
				this.nbPersonnes+
				" personnes - "+this.sejour.toString();
	}

	public int getNbPersonnes()
	{
		return nbPersonnes;
	}

	public void setNbPersonnes(int nbPersonnes)
	{
		this.nbPersonnes = nbPersonnes;
	}
	
	public void setNbPersonnes(String nbPersonnes)
	{
		this.nbPersonnes = Integer.parseInt(nbPersonnes);
	}

	public String getPays()
	{
		return pays;
	}

	public void setPays(String pays)
	{
		this.pays = pays;
	}

	public Sejour getSejour()
	{
		return sejour;
	}

	public void setSejour(Sejour sejour)
	{
		this.sejour = sejour;
	}
	
	public double getPrixTotal()
	{
		return this.nbPersonnes*this.sejour.getPrix();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
