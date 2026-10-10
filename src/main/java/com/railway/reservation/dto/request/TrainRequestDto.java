package com.railway.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.railway.reservation.entity.enums.TrainType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

@Schema(description = "Train creation and update request payload")
public class TrainRequestDto {

    @NotBlank(message = "Train number cannot be empty")
    @Size(min = 4, max = 10, message = "Train number must be between 4 and 10 characters")
    @Schema(description = "Unique 5-digit or alphanumeric train number", example = "12951")
    private String trainNumber;

    @NotBlank(message = "Train name cannot be empty")
    @Size(max = 100, message = "Train name must not exceed 100 characters")
    @Schema(description = "Official train name", example = "Mumbai Rajdhani Express")
    private String trainName;

    @NotNull(message = "Train type is required")
    @Schema(description = "Train category type", example = "RAJDHANI")
    private TrainType trainType;

    @NotNull(message = "Source station ID is required")
    @Schema(description = "ID of departure/origin station", example = "1")
    private Long sourceStationId;

    @NotNull(message = "Destination station ID is required")
    @Schema(description = "ID of destination station", example = "2")
    private Long destinationStationId;

    @NotNull(message = "Departure time is required")
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(description = "Departure time (HH:mm:ss)", example = "16:55:00", type = "string")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required")
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(description = "Arrival time (HH:mm:ss)", example = "08:35:00", type = "string")
    private LocalTime arrivalTime;

    @NotNull(message = "Total distance is required")
    @Positive(message = "Distance must be greater than zero")
    @Schema(description = "Total journey distance in kilometers", example = "1384.0")
    private Double totalDistanceKm;

    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be greater than zero")
    @Schema(description = "Base fare amount in INR", example = "500.0")
    private Double baseFare;

    public TrainRequestDto() {
    }

    public TrainRequestDto(String trainNumber, String trainName, TrainType trainType,
                           Long sourceStationId, Long destinationStationId,
                           LocalTime departureTime, LocalTime arrivalTime,
                           Double totalDistanceKm, Double baseFare) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.trainType = trainType;
        this.sourceStationId = sourceStationId;
        this.destinationStationId = destinationStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalDistanceKm = totalDistanceKm;
        this.baseFare = baseFare;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public Long getSourceStationId() {
        return sourceStationId;
    }

    public void setSourceStationId(Long sourceStationId) {
        this.sourceStationId = sourceStationId;
    }

    public Long getDestinationStationId() {
        return destinationStationId;
    }

    public void setDestinationStationId(Long destinationStationId) {
        this.destinationStationId = destinationStationId;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getTotalDistanceKm() {
        return totalDistanceKm;
    }

    public void setTotalDistanceKm(Double totalDistanceKm) {
        this.totalDistanceKm = totalDistanceKm;
    }

    public Double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(Double baseFare) {
        this.baseFare = baseFare;
    }
}
