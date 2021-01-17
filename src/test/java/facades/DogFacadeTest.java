package facades;

import dtos.DogDTO;
import entities.Dog;
import utils.EMF_Creator;
import entities.Role;
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
public class DogFacadeTest {

    private static EntityManagerFactory emf;
    private static DogFacade facade;
    private User user;
    private User admin;
    private User both;

    public DogFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DogFacade.getDogFacade(emf);
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
    public void testAddDog() throws MissingDogInfoException {
        int amountBefore = facade.getDogs(user.getUserName()).size();
        DogDTO dDTO = new DogDTO(null, "henrik", "the dog", "1234", "12345");
        facade.addDog(user.getUserName(), dDTO);
        int amountNow = facade.getDogs(user.getUserName()).size();
        assertEquals(amountNow, amountBefore + 1);
    }

    @Test
    public void testAddDogEmptyName() throws MissingDogInfoException {
        Assertions.assertThrows(MissingDogInfoException.class, () -> {
            DogDTO dDTO = new DogDTO(null, "", "the dog", "1234", "12345");
            facade.addDog(user.getUserName(), dDTO);
        });
    }
    
    @Test
    public void testGetDogs() {
        String actualDogName = facade.getDogs(user.getUserName()).get(0).getName();
        assertEquals("hej", actualDogName);
    }
    
}
