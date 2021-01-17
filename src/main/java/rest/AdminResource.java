/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facades.AdminFacade;
import javax.annotation.security.RolesAllowed;
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

/**
 *
 * @author Tas
 */
@Path("admin")
public class AdminResource {

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
    @RolesAllowed("admin")
    public String getCalls() {
        long searches = adminFacade.getAmountOfSearches();
        return "{\"searches\":\"" + searches + "\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("get-calls/{breedName}")
    @RolesAllowed("admin")
    public String getSpecificCalls(@PathParam("breedName") String breedName) {
        long searches = adminFacade.getAmountOfSpecificSearches(breedName);
        return "{\"searches\":\"" + searches + "\"}";
    }

}
