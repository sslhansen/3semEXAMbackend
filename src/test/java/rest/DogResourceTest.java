package rest;

import entities.Dog;
import entities.Role;
import entities.Searches;
import entities.User;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class DogResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private User user;
    private User admin;
    private User both;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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
    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;
    //Utility method to login and set the returned securityToken

    private static void login(String username, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", username, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/dog").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/dog/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    //Working, but its in array
//    @Test
//    public void testGetDogsForUser() {
//        login("user", "test1");
//        given()
//                .contentType("application/json")
//                .accept(ContentType.JSON)
//                .header("x-access-token", securityToken)
//                .when()
//                .get("/dog/get-dogs/user").then()
//                .statusCode(200)
//                .body("name", equalTo("[hej]"));
//    }
    @Test
    public void testAddDog() {
        login("user", "test1");
        String json = String.format("{\n"
                + "    \"userName\": \"user\",\n"
                + "    \"name\": \"try\", \n"
                + "    \"dateOfBirth\": \"khj\",\n"
                + "    \"info\": \"Its pretty cute\",\n"
                + "    \"breed\": \"meow\"\n"
                + "}");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(json)
                .when().post("/dog/add-dog")
                .then()
                .statusCode(200)
                .body("msg", equalTo("Dog added"));
    }

    @Test
    public void testAddDogFailing() {
        login("user", "test1");
        String json = String.format("{\n"
                + "    \"userName\": \"\",\n"
                + "    \"name\": \"try\", \n"
                + "    \"dateOfBirth\": \"khj\",\n"
                + "    \"info\": \"Its pretty cute\",\n"
                + "    \"breed\": \"meow\"\n"
                + "}");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(json)
                .when().post("/dog/add-dog")
                .then()
                .statusCode(200)
                .body("msg", equalTo("Missing dog information!"));
    }

}
