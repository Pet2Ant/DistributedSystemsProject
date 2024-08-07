package gr.athtech.DistributedSystems.resource;

import gr.athtech.DistributedSystems.dao.HealthDao;
import gr.athtech.DistributedSystems.model.HealthData;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.util.List;

@Path("/healthdata")
public class HealthDataResource {

    private final HealthDao healthDao = new HealthDao();

    @GET
    @Path("/hello-world")
    @Produces("text/plain")
    @PermitAll
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/otp")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed({"ADMIN","PHYSICIAN"})
    public List<HealthData> displayOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate , @QueryParam("endDate") java.sql.Date endDate) {
        return healthDao.displayOverTimePeriod(startDate, endDate);
    }
    @GET
    @Path("/avgGlucose")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed({"ADMIN","PHYSICIAN"})
    public Double averageBloodGlucoseLevelOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate , @QueryParam("endDate") java.sql.Date endDate) {
        return healthDao.averageBloodGlucoseLevelOverTimePeriod(startDate, endDate);
    }
    @GET
    @Path("/avgCarbs")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed({"ADMIN","PHYSICIAN"})
    public Double averageCarbIntakeOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate , @QueryParam("endDate") java.sql.Date endDate) {
        return healthDao.averageCarbIntakeOverTimePeriod(startDate, endDate);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed({"ADMIN","PHYSICIAN"})
    public List<HealthData> getAllHealthData() {
        return healthDao.findAllHealthData();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed({"ADMIN","PHYSICIAN"})
    public HealthData getHealthDataById(@PathParam("id") int id) {
        return healthDao.findHealthDataById(id);
    }

    @GET
    @Path("/glucoseLevelOverTimePeriod")
    @Produces("image/png")
    @RolesAllowed({"ADMIN","PHYSICIAN"})
    public Response glucoseLevelOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate, @QueryParam("endDate") java.sql.Date endDate) {
        healthDao.glucoseLevelOverTimePeriod(startDate, endDate);
        File file = new File("BloodGlucoseChart.png");
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"BloodGlucoseChart.png\"");
        return response.build();
    }
    @GET
    @Path("/carbIntakeOverTimePeriod")
    @Produces("image/png")
    @RolesAllowed({"ADMIN","PHYSICIAN"})
    public Response carbIntakeOverTimePeriod(@QueryParam("startDate") java.sql.Date startDate, @QueryParam("endDate") java.sql.Date endDate) {
        healthDao.carbIntakeOverTimePeriod(startDate, endDate);
        File file = new File("CarbIntake.png");
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"CarbIntake.png\"");
        return response.build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed("ADMIN")
    public HealthData createHealthData(HealthData healthData) {
        healthDao.save(healthData);
        return healthData;
    }
    @POST
    @Path("/mass")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public List<HealthData> createHealthDatas(List<HealthData> healthDataList) {
            healthDao.createHealthDatas(healthDataList);
        return healthDataList;
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed("ADMIN")
    public HealthData updateHealthData(@PathParam("id") int id,HealthData healthData) {
        healthDao.changeHealthDataById(id, healthData);
        return healthData;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON )
    @Consumes(MediaType.APPLICATION_JSON )
    @RolesAllowed("ADMIN")
    public HealthData deleteHealthData(@PathParam("id") int healthdataId) {
        HealthData healthData = healthDao.findHealthDataById(healthdataId);
        healthDao.removeHealthDataById(healthdataId);
        return healthData;
    }


}