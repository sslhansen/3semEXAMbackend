/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tas
 */
@Entity
@NamedQuery(name = "Searches.deleteAllRows", query = "DELETE from Searches")
public class Searches implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    Date TimeDate;

    private String breedName;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Searches(String breedName) {
        this.breedName = breedName;
        TimeDate = new Date();
    }

    public Searches() {
        TimeDate = new Date();
        this.breedName = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

}
