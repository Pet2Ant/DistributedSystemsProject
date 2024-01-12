package gr.athtech.athtecheshop.resource;

import gr.athtech.athtecheshop.dao.HealthDao;
import gr.athtech.athtecheshop.model.HealthData;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/healthdata")
public class HealthDataResource {

    private final HealthDao healthDao = new HealthDao();

    @GET
    @Path("/hello-world")
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public List<HealthData> getAllHealthData() {
        return healthDao.findAllHealthData();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public HealthData getHealthDataById(@PathParam("id") int id) {
        return healthDao.findHealthDataById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public HealthData createHealthData(HealthData healthData) {
        healthDao.save(healthData);
        return healthData;
    }

    @PUT
    //   @Path("/")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public HealthData updateHealthData(HealthData healthData) {
        int existingHealthData = healthData.getId();
        healthDao.changeHealthData(existingHealthData, healthData);
        return healthData;
    }

    @DELETE
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public HealthData deleteHealthData(@PathParam("productId") int healthdataId) {
        HealthData healthData = healthDao.findHealthDataById(healthdataId);
        healthDao.removeHealthDataById(healthdataId);
        return healthData;
    }
}