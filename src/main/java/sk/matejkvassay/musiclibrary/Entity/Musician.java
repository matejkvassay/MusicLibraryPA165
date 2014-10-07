/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.matejkvassay.musiclibrary.Entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
/**
 *
 * @author Matej Kvassay <www.matejkvassay.sk>
 */

@Entity
public class Musician {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String biography;
    
   @OneToMany(mappedBy="musician")
    private Set<Album> albums;
    
    @OneToMany(mappedBy="musician")
    private Set<Song>songs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Set<Album> getAlbum() {
        return albums;
    }

    public void setAlbum(Set<Album> albums) {
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentary() {
        return biography;
    }

    public void setCommentary(String commentary) {
        this.biography = commentary;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Musician other = (Musician) obj;
        return true;
    }
    
    
}
