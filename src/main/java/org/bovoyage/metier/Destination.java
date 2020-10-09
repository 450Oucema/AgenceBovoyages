package org.bovoyage.metier;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "destinations")
public class Destination implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9047780353115578697L;

	@Id
	@Column(name = "iddestination")
	private int identifiant;

	@Column(name = "destination")
	private String pays;

	@Column(name = "description")
	private String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "destination")
	private List<Image> imagesDestinations = new ArrayList<Image>();

	@Transient
	private Vector<String> images = null;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "destination")
	private Set<Sejour> sejours = new HashSet<Sejour>();

	public Destination()
	{
	}

	public Destination(int identifiant, String destination)
	{
		this.identifiant = identifiant;
		this.pays = destination;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getPays()
	{
		return pays;
	}

	public void setPays(String destination)
	{
		this.pays = destination;
	}

	public int getIdentifiant()
	{
		return identifiant;
	}

	public void setIdentifiant(int identifiant)
	{
		this.identifiant = identifiant;
	}

	public Vector<String> getImages()
	{
		return images;
	}

	public void setImages(Vector<String> images)
	{
		this.images = images;
	}

	public void addImage(String image)
	{
		images.add(image);
	}

	public void addSejour(Sejour sejour)
	{
		this.sejours.add(sejour);
	}

	@Override
	public String toString() {
		return "Destination{" +
				"identifiant=" + identifiant +
				", pays='" + pays + '\'' +
				", description='" + description + '\'' +
				", imagesDestinations=" + imagesDestinations.toString() +
				'}';
	}

	public List<Image> getImagesDestinations() {
		return imagesDestinations;
	}

	public void setImagesDestinations(List<Image> imagesDestinations) {
		this.imagesDestinations = imagesDestinations;
	}

	public Set<Sejour> getSejours() {
		return sejours;
	}

	public void setSejours(Set<Sejour> sejours) {
		this.sejours = sejours;
	}

	public Sejour getSejour(int id)
	{
		Sejour sejour = null;
		for (Sejour value : this.sejours) {
			sejour = value;
			if (sejour.getId() == id)
				break;
		}

		return sejour;
	}

	public Sejour getSejour(String id)
	{
		return this.getSejour(Integer.parseInt(id));
	}
}
