package gr.athtech.athtecheshop.dao;

import gr.athtech.athtecheshop.model.HealthData;
import java.util.List;

public interface HealthDaoInterface {
     // POST 
     void save(HealthData healthData);
     // GET BY ID
     HealthData findHealthDataById(int healthDataId);
     
     // GET ALL
     List<HealthData> findAllHealthData();
     
     // PUT
     boolean changeHealthData(int healthDataId, HealthData healthData);

     // DELETE BY ID
     boolean removeHealthDataById(int healthDataId);
}
