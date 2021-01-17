package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

//
//    @GET
//    @Path("extern")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJokes() throws IOException, InterruptedException, ExecutionException, TimeoutException {
//        ExecutorService threadPool = Executors.newCachedThreadPool();
//        Callable< RandomDogDTO> dogTask = new Callable<RandomDogDTO>() {
//            @Override
//            public RandomDogDTO call() throws Exception {
//                String randomDog = HttpUtils.fetchData("https://dog.ceo/api/breeds/image/random");
//                RandomDogDTO randomDogDTO = gson.fromJson(randomDog, RandomDogDTO.class);
//                return randomDogDTO;
//            }
//        };
//
////        Callable<QuoteDTO> quoteTask = new Callable<QuoteDTO>() {
////            @Override
////            public QuoteDTO call() throws Exception {
////                String randomQuote = HttpUtils.fetchData("https://programming-quotes-api.herokuapp.com/quotes/random");
////                QuoteDTO randomQuoteDTO = gson.fromJson(randomQuote, QuoteDTO.class);
////                return randomQuoteDTO;
////            }
////        };
//
////        Callable<FoodDTO> foodTask = new Callable<FoodDTO>() {
////            @Override
////            public FoodDTO call() throws Exception {
////                String food = HttpUtils.fetchData("https://foodish-api.herokuapp.com/api");
////                FoodDTO foodDTO = gson.fromJson(food, FoodDTO.class);
////                return foodDTO;
////            }
////        };
//
//        Callable<BoredDTO> boredTask = new Callable<BoredDTO>() {
//            @Override
//            public BoredDTO call() throws Exception {
//                String bored = HttpUtils.fetchData("https://www.boredapi.com/api/activity");
//                BoredDTO boredDTO = gson.fromJson(bored, BoredDTO.class);
//                return boredDTO;
//            }
//        };
//
//        Callable<KanyeDTO> kanyeTask = new Callable<KanyeDTO>() {
//            @Override
//            public KanyeDTO call() throws Exception {
//                String kanye = HttpUtils.fetchData("https://api.kanye.rest/");
//                KanyeDTO kanyeDTO = gson.fromJson(kanye, KanyeDTO.class);
//                return kanyeDTO;
//            }
//        };
//
//        Future<RandomDogDTO> futureDog = threadPool.submit(dogTask);
////        Future<QuoteDTO> futureQuote = threadPool.submit(quoteTask);
////        Future<FoodDTO> futureFood = threadPool.submit(foodTask);
//        Future<BoredDTO> futureBored = threadPool.submit(boredTask);
//        Future<KanyeDTO> futureKanye = threadPool.submit(kanyeTask);
//        RandomDogDTO dog = futureDog.get(2, TimeUnit.SECONDS);
////        QuoteDTO quote = futureQuote.get(2, TimeUnit.SECONDS);
////        FoodDTO food = futureFood.get(2, TimeUnit.SECONDS);
//        BoredDTO bored = futureBored.get(2, TimeUnit.SECONDS);
//        KanyeDTO kanye = futureKanye.get(2, TimeUnit.SECONDS);
//
//        CombinedDTO combined = new CombinedDTO(bored, kanye, dog);
//        String json = GSON.toJson(combined);
//        return json;
//    }
}
