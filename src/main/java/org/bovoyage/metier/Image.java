package org.bovoyage.metier;

import javax.persistence.*;

@Entity
@Table(name = "imagesdestinations")
public class Image
{
    @ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "iddestination")
    private Destination destination;

    @Id
    private String image;

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Image{" +
                "image='" + image + '\'' +
                '}';
    }
}
