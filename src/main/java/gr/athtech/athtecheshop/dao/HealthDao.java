package gr.athtech.athtecheshop.dao;

import gr.athtech.athtecheshop.model.HealthData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthDao implements HealthDaoInterface {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/health_data";
    private static final String USER = "postgres";
    private static final String PASS = "PCa173!!";
    private static final String JDBC_DRIVER = "org.postgresql.Driver";



    // POST
    public void save(HealthData healthData) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand = "insert into health_data (id, date, blood_glucose_level, carb_intake, medication_dose) values(?,?,?,?,?);";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(sqlCommand,
                    Statement.RETURN_GENERATED_KEYS );
        ) {
            stmt.setInt(1, healthData.getId());
            stmt.setString(2, String.valueOf(healthData.getDate()));
            stmt.setDouble(3,healthData.getBloodGlucoseLevel());
            stmt.setDouble(4,healthData.getCarbIntake());
            stmt.setDouble(5,healthData.getMedicationDose());
            stmt.execute();
            ResultSet results = stmt.getGeneratedKeys();
            results.next(); // Assume just one auto-generated key; otherwise, use a while loop here
            int index = results.getInt(1);
            healthData.setId(index);
         } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // GET BY ID
    @Override
    public HealthData findHealthDataById(int healthDataId) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand = "select * from health_data where id = ?;";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand );
        ) {
            stmt.setInt(1, healthDataId);
            ResultSet results = stmt.executeQuery();
            results.next();
            HealthData healthData = new HealthData();
            healthData.setId(results.getInt("id"));
            healthData.setDate(results.getDate("date").toLocalDate());
            healthData.setBloodGlucoseLevel(results.getDouble("blood_glucose_level"));
            healthData.setCarbIntake(results.getDouble("carb_intake"));
            healthData.setMedicationDose(results.getDouble("medication_dose"));
            return healthData;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // GET ALL
    @Override
    public List<HealthData> findAllHealthData() {
        List<HealthData> healthDatas = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand = "select * from health_data ;";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand );
        ) {
            ResultSet results = stmt.executeQuery();
           while( results.next()) {
               HealthData healthData = new HealthData();
               healthData.setId(results.getInt("id"));
               healthData.setDate(results.getDate("date").toLocalDate());
               healthData.setBloodGlucoseLevel(results.getDouble("blood_glucose_level"));
               healthData.setCarbIntake(results.getDouble("carb_intake"));
               healthData.setMedicationDose(results.getDouble("medication_dose"));
               healthDatas.add(healthData);
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return healthDatas;
    }
    // PUT
    @Override
    public boolean changeHealthData(int healthDataId, HealthData healthData) {
        HealthData healthData1 = findHealthDataById(healthDataId);
        if (healthData1==null) return false;

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand = "update health_data set (blood_glucose_level, carb_intake, medication_dose) = (?,?,?) where id =?;";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand );
        ) {
            stmt.setDouble(1,healthData1.getBloodGlucoseLevel());
            stmt.setDouble(2,healthData1.getCarbIntake());
            stmt.setDouble(3,healthData1.getMedicationDose());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    // DELETE BY ID
    @Override
    public boolean removeHealthDataById(int healthDataId) {

        HealthData healthData1 = findHealthDataById(healthDataId);
        if (healthData1==null) return false;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand = "delete from health_data  where id =?;";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand );
        ) {
            stmt.setInt(1,healthDataId);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
