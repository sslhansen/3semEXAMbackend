/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.DogDTO;
import entities.Dog;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Tas
 */
public class DogFacade {

    private static EntityManagerFactory emf;
    private static DogFacade instance;
    private DogFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static DogFacade getDogFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }
    
    public void addDog(String userName, DogDTO dogDTO) {
        EntityManager em = emf.createEntityManager();
       try {
           em.getTransaction().begin();
           User user = em.find(User.class, userName);
           Dog dog = new Dog(dogDTO.getName(), dogDTO.getDateOfBirth(), dogDTO.getInfo(), dogDTO.getBreed());
           user.addDog(dog);
           em.getTransaction().commit();
       } finally {
           em.close();
       }
    }
    
}
