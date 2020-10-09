package org.bovoyage.metier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sejour")
public class Sejour implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8032475442746653441L;

	@Id
	@Column(name = "idsejour")
	private int id;

	@Column(name = "datedepart")
	private Date depart;

	@Column(name = "dateretour")
	private Date retour;

	@Column(name = "prixhtdestination")
	private double prix;

	@ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "iddestination")
	private Destination destination;
	
	public Sejour()
	{}
	
	public Sejour(Date depart, Date retour, double prix)
	{
		this.depart = depart;
		this.retour = retour;
		this.prix = prix;
	}

	public Date getDepart()
	{
		return depart;
	}

	public void setDepart(Date depart)
	{
		this.depart = depart;
	}

	public Date getRetour()
	{
		return retour;
	}

	public void setRetour(Date retour)
	{
		this.retour = retour;
	}
	
	public double getPrix()
	{
		return prix;
	}

	public void setPrix(double prix)
	{
		this.prix = prix;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	public boolean equals(Sejour s)
	{
		return this.id == s.id;
	}
	
	public boolean equals(int id)
	{
		return this.id==id;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Sejour{" +
				"id=" + id +
				", depart=" + depart +
				", retour=" + retour +
				", prix=" + prix +
				", destination=" + destination.getPays() +
				'}';
	}
}
