/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.DogDTO;
import entities.Dog;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

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

    public List<DogDTO> getDogs(String userName) {
        List<DogDTO> dogs = new ArrayList();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            TypedQuery<Dog> userQuery = em.createQuery("SELECT a FROM Dog a WHERE a.user.userName = :username", Dog.class);
            List<Dog> doggies = userQuery.setParameter("username", userName).getResultList();
            for (Dog dawg : doggies) {
                dogs.add(new DogDTO(dawg.getName(), dawg.getDateOfBirth(), dawg.getInfo(), dawg.getBreed()));
            }
            em.getTransaction().commit();
            return dogs;
        } finally {
            em.close();
        }
    }

}
