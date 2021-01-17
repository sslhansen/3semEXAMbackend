package facades;

import dtos.DogDTO;
import entities.Dog;
import utils.EMF_Creator;
import entities.RenameMe;
import entities.Role;
import entities.Searches;
import entities.User;
import errorhandling.MissingDogInfoException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class AdminFacadeTest {

    private static EntityManagerFactory emf;
    private static AdminFacade facade;
    private User user;
    private User admin;
    private User both;

    public AdminFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AdminFacade.getAdminFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Searches.deleteAllRows").executeUpdate();
            em.createNamedQuery("Dog.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            user = new User("user", "test1");
            admin = new User("admin", "test1");
            both = new User("user_admin", "test1");
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            Dog dog1 = new Dog("hej", "1234", "med", "test");
            Dog dog2 = new Dog("hej2", "5644", "med2", "test2");
            Searches s1 = new Searches("potatis");
            Searches s2 = new Searches();
            em.persist(s1);
            em.persist(s2);
            user.addDog(dog1);
            admin.addDog(dog2);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAmountOfSearches() {
        assertEquals(facade.getAmountOfSearches(), 2);
    }

    @Test
    public void testGetAmountOfSpecificSearches() {
        assertEquals(facade.getAmountOfSpecificSearches("potatis"), 1);
    }

    @Test
    public void testAddSearch() {
        long amountBefore = facade.getAmountOfSearches();
        facade.addSearch();
        long amountNow = facade.getAmountOfSearches();
        assertEquals(amountNow, amountBefore + 1);
    }

    @Test
    public void testAddSpecificSearch() {
        long amountBefore = facade.getAmountOfSpecificSearches("potatis");
        facade.addSpecificSearch("potatis");
        long amountNow = facade.getAmountOfSpecificSearches("potatis");
        assertEquals(amountNow, amountBefore + 1);
    }

}
