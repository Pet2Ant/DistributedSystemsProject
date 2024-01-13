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

     List<HealthData> displayOverTimePeriod(java.sql.Date startDate, java.sql.Date endDate);
     Double averageBloodGlucoseLevelOverTimePeriod(java.sql.Date startDate, java.sql.Date endDate);

     void glucoseLevelOverTimePeriod(java.sql.Date startDate, java.sql.Date endDate);

    void carbIntakeOverTimePeriod(Date startDate, Date endDate);

    Double averageCarbIntakeOverTimePeriod(java.sql.Date startDate, java.sql.Date endDate);

     void createHealthDatas(List<HealthData> healthDataList);


     // PUT
     boolean changeHealthDataById(int healthDataId, HealthData healthData);

     // DELETE BY ID
     boolean removeHealthDataById(int healthDataId);


}
