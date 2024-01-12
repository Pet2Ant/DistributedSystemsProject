package gr.athtech.DistributedSystems.resource;

import gr.athtech.DistributedSystems.dao.HealthDao;
import gr.athtech.DistributedSystems.model.HealthData;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.Date;
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
    @Path("/otp")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public List<HealthData> displayOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate , @QueryParam("endDate") java.sql.Date endDate) {
        return healthDao.displayOverTimePeriod(startDate, endDate);
    }
    @GET
    @Path("/avgGlucose")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public Double averageBloodGlucoseLevelOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate , @QueryParam("endDate") java.sql.Date endDate) {
        return healthDao.averageBloodGlucoseLevelOverTimePeriod(startDate, endDate);
    }
    @GET
    @Path("/avgCarbs")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public Double averageCarbIntakeOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate , @QueryParam("endDate") java.sql.Date endDate) {
        return healthDao.averageCarbIntakeOverTimePeriod(startDate, endDate);
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
    @POST
    @Path("/mass")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<HealthData> createHealthDatas(List<HealthData> healthDataList) {
            healthDao.createHealthDatas(healthDataList);
        return healthDataList;
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public HealthData updateHealthData(@PathParam("id") int id,HealthData healthData) {
        healthDao.changeHealthDataById(id, healthData);
        return healthData;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    public HealthData deleteHealthData(@PathParam("id") int healthdataId) {
        HealthData healthData = healthDao.findHealthDataById(healthdataId);
        healthDao.removeHealthDataById(healthdataId);
        return healthData;
    }


}