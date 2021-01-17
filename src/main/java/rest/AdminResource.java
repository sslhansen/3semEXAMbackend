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
import dtos.DogDTO;
import entities.Dog;
import errorhandling.MissingDogInfoException;
import facades.AdminFacade;
import facades.DogFacade;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
@Path("admin")
public class AdminResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private Gson gson = new Gson();
    private final DogFacade dogFacade = DogFacade.getDogFacade(EMF);
    private final AdminFacade adminFacade = AdminFacade.getAdminFacade(EMF);
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
    @Produces({MediaType.APPLICATION_JSON})
    @Path("get-calls")
    //@RolesAllowed("admin")
    public String getCalls() {
        long searches = adminFacade.getAmountOfSearches();
         return "{\"searches\":\"" + searches + "\"}";
    }

}
