package gr.athtech.DistributedSystems.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Entity
@Getter
@Setter
public class HealthData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    @JsonProperty("blood_glucose_level")
    private double bloodGlucoseLevel;
    @JsonProperty("carb_intake")
    private Double carbIntake;
    @JsonProperty("medication_dose")
    private Double medicationDose;
}
