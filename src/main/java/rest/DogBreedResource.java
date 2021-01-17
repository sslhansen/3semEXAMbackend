/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.CombinedDTO;
import entities.Searches;
import errorhandling.MissingDogInfoException;
import facades.AdminFacade;
import facades.DogFacade;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 *
 * @author Tas
 */
@Path("dog-breed")
public class DogBreedResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private Gson gson = new Gson();
    private final DogFacade dogFacade = DogFacade.getDogFacade(EMF);
    private final AdminFacade adminFacade = AdminFacade.getAdminFacade(EMF);
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{breedName}")
    public String getBreedInformation(@PathParam("breedName") String breedName) throws IOException, InterruptedException, ExecutionException, TimeoutException, MissingDogInfoException {
        try {
            Callable<String> imageTask = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String breedImageFetch = HttpUtils.fetchData("https://dog-image.cooljavascript.dk/api/breed/random-image/" + breedName);
                    JsonObject json = JsonParser.parseString(breedImageFetch).getAsJsonObject();
                    return json.get("image").getAsString();
                }
            };

            Callable<String> infoTask = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String breedImageFetch = HttpUtils.fetchData("https://dog-info.cooljavascript.dk/api/breed/" + breedName);
                    JsonObject json = JsonParser.parseString(breedImageFetch).getAsJsonObject();
                    return json.get("info").getAsString();
                }
            };

            Callable<String> infoTaskWikipedia = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String breedImageFetch = HttpUtils.fetchData("https://dog-info.cooljavascript.dk/api/breed/" + breedName);
                    JsonObject json = JsonParser.parseString(breedImageFetch).getAsJsonObject();
                    return json.get("wikipedia").getAsString();
                }
            };

            Callable<String> factTask = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String breedImageFetch = HttpUtils.fetchData("https://dog-api.kinduff.com/api/facts");
                    JsonObject json = JsonParser.parseString(breedImageFetch).getAsJsonObject();
                    return json.get("facts").getAsString();
                }
            };

            Future<String> futureBreedImage = threadPool.submit(imageTask);
            Future<String> futureInfo = threadPool.submit(infoTask);
            Future<String> futureWikipedia = threadPool.submit(infoTaskWikipedia);
            Future<String> futureFact = threadPool.submit(factTask);

            String imageString = futureBreedImage.get(2, TimeUnit.SECONDS);
            String infoString = futureInfo.get(2, TimeUnit.SECONDS);
            String infoStringWikipedia = futureWikipedia.get(2, TimeUnit.SECONDS);
            String factString = futureFact.get(2, TimeUnit.SECONDS);
            CombinedDTO combined = new CombinedDTO(breedName, infoString, infoStringWikipedia, imageString, factString);
            return GSON.toJson(combined);
        } catch (ExecutionException e) {
            return "{\"msg\":\"Missing dog information!\"}";
        }
    }

}
