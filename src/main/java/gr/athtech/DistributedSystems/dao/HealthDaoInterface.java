package gr.athtech.DistributedSystems.dao;

import gr.athtech.DistributedSystems.model.HealthData;

import java.sql.Date;
import java.util.List;

public interface HealthDaoInterface {
     // POST 
     void save(HealthData healthData);
     // GET BY ID
     HealthData findHealthDataById(int healthDataId);
     
     // GET ALL
     List<HealthData> findAllHealthData();

//     List<HealthData> displayOverTimePeriod(Date startDate, Date endDate);
     
     // PUT
     boolean changeHealthDataById(int healthDataId, HealthData healthData);

     // DELETE BY ID
     boolean removeHealthDataById(int healthDataId);


}
