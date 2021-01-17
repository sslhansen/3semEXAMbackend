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
import dtos.DogDTO;
import entities.Dog;
import errorhandling.MissingDogInfoException;
import facades.DogFacade;
import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("dog")
public class DogResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private Gson gson = new Gson();
    private final DogFacade dogFacade = DogFacade.getDogFacade(EMF);

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-dog")
    //@RolesAllowed("user")
    public String addDog(String jsonString) throws MissingDogInfoException {
        DogDTO dogDTO;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String userName = json.get("userName").getAsString();
            String name = json.get("name").getAsString();
            String dateOfBirth = json.get("dateOfBirth").getAsString();
            String info = json.get("info").getAsString();
            String breed = json.get("breed").getAsString();
            dogDTO = new DogDTO(null, name, dateOfBirth, info, breed);
            dogFacade.addDog(userName, dogDTO);
        } catch (MissingDogInfoException s) {
            return "{\"msg\":\"Missing dog information!\"}";
        } catch (NullPointerException e) {
            return "{\"msg\":\"Missing dog information!\"}";
        }
        return "{\"msg\":\"Dog added\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get-dogs/{userName}")
    public String getDogs(@PathParam("userName") String userName) {
        List<DogDTO> dogs = dogFacade.getDogs(userName);
        return gson.toJson(dogs);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get-dogs/breeds")
    public String getDogs() throws IOException {
        String breeds = HttpUtils.fetchData("https://dog-info.cooljavascript.dk/api/breed");
        JsonObject json = JsonParser.parseString(breeds).getAsJsonObject();
        return GSON.toJson(json.getAsJsonArray("dogs"));
    }

    
    
}
