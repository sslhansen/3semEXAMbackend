package facades;

import entities.Searches;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

public class AdminFacade {

    private static EntityManagerFactory emf;
    private static AdminFacade instance;

    private AdminFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static AdminFacade getAdminFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AdminFacade();
        }
        return instance;
    }

    public void addSearch() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Searches s = new Searches();
            em.persist(s);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Long getAmountOfSearches() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            long searchCount = (long) em.createQuery("SELECT COUNT(e) FROM Searches e").getSingleResult();
            em.getTransaction().commit();
            return searchCount;
        } finally {
            em.close();
        }

    }

}
