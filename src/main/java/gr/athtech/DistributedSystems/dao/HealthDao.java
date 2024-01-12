package gr.athtech.DistributedSystems.dao;

import gr.athtech.DistributedSystems.model.HealthData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthDao implements HealthDaoInterface {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/health";
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
        String sqlCommand = "insert into health_data (id, blood_glucose_level, carb_intake, medication_dose, date) values(?,?,?,?,?);";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand,
                     Statement.RETURN_GENERATED_KEYS );
        ) {
            stmt.setInt(1, healthData.getId());
            stmt.setDouble(2, healthData.getBloodGlucoseLevel());
            stmt.setDouble(3, healthData.getCarbIntake());
            stmt.setDouble(4, healthData.getMedicationDose());
            stmt.setDate(5, healthData.getDate());
            stmt.execute();
            ResultSet results = stmt.getGeneratedKeys();
            results.next();
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
            if (results.next()) {
                HealthData healthData = new HealthData();
                healthData.setId(results.getInt("id"));
                healthData.setDate(results.getDate("date"));
                healthData.setBloodGlucoseLevel(results.getDouble("blood_glucose_level"));
                healthData.setCarbIntake(results.getDouble("carb_intake"));
                healthData.setMedicationDose(results.getDouble("medication_dose"));
                return healthData;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Double averageBloodGlucoseLevelOverTimePeriod(java.sql.Date startDate, java.sql.Date endDate) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand;
        if(startDate != null && endDate != null && startDate.after(endDate)) return null;
        else if(startDate == null && endDate == null) {
            sqlCommand = "SELECT AVG(blood_glucose_level) FROM health_data;";
        } else if (startDate == null) {
            sqlCommand = "SELECT AVG(blood_glucose_level) FROM health_data WHERE date BETWEEN (SELECT MIN(date) FROM health_data) AND ?;";
        } else if (endDate == null) {
            sqlCommand = "SELECT AVG(blood_glucose_level) FROM health_data WHERE date BETWEEN ? AND (SELECT MAX(date) FROM health_data);";
        } else {
            sqlCommand = "SELECT AVG(blood_glucose_level) FROM health_data WHERE date BETWEEN ? AND ?;";
        }
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand);
        ) {
            if(startDate != null) stmt.setDate(1, startDate);
            if(endDate != null) stmt.setDate(startDate == null ? 1 : 2, endDate);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return results.getDouble(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Double averageCarbIntakeOverTimePeriod(java.sql.Date startDate, java.sql.Date endDate) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand;
        if(startDate != null && endDate != null && startDate.after(endDate)) return null;
        else if(startDate == null && endDate == null) {
            sqlCommand = "SELECT AVG(carb_intake) FROM health_data;";
        } else if (startDate == null) {
            sqlCommand = "SELECT AVG(carb_intake) FROM health_data WHERE date BETWEEN (SELECT MIN(date) FROM health_data) AND ?;";
        } else if (endDate == null) {
            sqlCommand = "SELECT AVG(carb_intake) FROM health_data WHERE date BETWEEN ? AND (SELECT MAX(date) FROM health_data);";
        } else {
            sqlCommand = "SELECT AVG(carb_intake) FROM health_data WHERE date BETWEEN ? AND ?;";
        }
            // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand);
        ) {
            if(startDate != null) stmt.setDate(1, startDate);
            if(endDate != null) stmt.setDate(startDate == null ? 1 : 2, endDate);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return results.getDouble(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void createHealthDatas(List<HealthData> healthDataList) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand = "INSERT INTO health_data (id, blood_glucose_level, carb_intake, medication_dose, date) VALUES (?, ?, ?, ?, ?);";
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS);
        ) {
            for (HealthData healthData : healthDataList) {
                stmt.setInt(1, healthData.getId());
                stmt.setDouble(2, healthData.getBloodGlucoseLevel());
                stmt.setDouble(3, healthData.getCarbIntake());
                stmt.setDouble(4, healthData.getMedicationDose());
                stmt.setDate(5, healthData.getDate());
                stmt.addBatch();
            }
            stmt.executeBatch();
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
        String sqlCommand = "select * from health_data;";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand );
        ) {
            ResultSet results = stmt.executeQuery();
            while( results.next()) {
                HealthData healthData = new HealthData();
                healthData.setId(results.getInt("id"));
                healthData.setBloodGlucoseLevel(results.getDouble("blood_glucose_level"));
                healthData.setCarbIntake(results.getDouble("carb_intake"));
                healthData.setMedicationDose(results.getDouble("medication_dose"));
                healthData.setDate(results.getDate("date"));
                healthDatas.add(healthData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return healthDatas;
    }

    //    Get over a time period
    public List<HealthData> displayOverTimePeriod(java.sql.Date startDate, java.sql.Date endDate) {
        List<HealthData> healthDatas = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand;
        if(startDate != null && endDate != null && startDate.after(endDate)) return null;
        else if(startDate == null && endDate == null) {
            sqlCommand = "SELECT * FROM health_data;";
        } else if (startDate == null) {
            sqlCommand = "SELECT * FROM health_data WHERE date BETWEEN (SELECT MIN(date) FROM health_data) AND ?;";
        } else if (endDate == null) {
            sqlCommand = "SELECT * FROM health_data WHERE date BETWEEN ? AND (SELECT MAX(date) FROM health_data);";
        } else {
            sqlCommand = "SELECT * FROM health_data WHERE date BETWEEN ? AND ?;";
        }
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand );
        ) {
            if(startDate != null) stmt.setDate(1, startDate);
            if(endDate != null) stmt.setDate(startDate == null ? 1 : 2, endDate);
            ResultSet results = stmt.executeQuery();
            while( results.next()) {
                HealthData healthData = new HealthData();
                healthData.setId(results.getInt("id"));
                healthData.setBloodGlucoseLevel(results.getDouble("blood_glucose_level"));
                healthData.setCarbIntake(results.getDouble("carb_intake"));
                healthData.setMedicationDose(results.getDouble("medication_dose"));
                healthData.setDate(results.getDate("date"));
                healthDatas.add(healthData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return healthDatas;
    }


    // PUT
    @Override
    public boolean changeHealthDataById(int healthDataId, HealthData healthData) {
        HealthData healthData1 = findHealthDataById(healthDataId);
        if (healthData1==null) return false;

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String sqlCommand = "update health_data set blood_glucose_level = ?, carb_intake = ?, medication_dose = ?, date = ? where id = ?;";
        // Open a connection
        try( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sqlCommand );
        ) {
            stmt.setDouble(1, healthData.getBloodGlucoseLevel());
            stmt.setDouble(2, healthData.getCarbIntake());
            stmt.setDouble(3, healthData.getMedicationDose());
            stmt.setDate(4, healthData1.getDate());
            stmt.setInt(5, healthDataId);
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
        String sqlCommand = "delete from health_data where id =?;";
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