package com.railway.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.railway.reservation.entity.enums.TrainType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.List;

@Schema(description = "Train details response")
public class TrainResponseDto {

    @Schema(description = "Train ID", example = "1")
    private Long id;

    @Schema(description = "Train Number", example = "12951")
    private String trainNumber;

    @Schema(description = "Train Name", example = "Mumbai Rajdhani Express")
    private String trainName;

    @Schema(description = "Train Type", example = "RAJDHANI")
    private TrainType trainType;

    @Schema(description = "Origin Station details")
    private StationResponseDto sourceStation;

    @Schema(description = "Destination Station details")
    private StationResponseDto destinationStation;

    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(description = "Scheduled Departure Time", example = "16:55:00", type = "string")
    private LocalTime departureTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(description = "Scheduled Arrival Time", example = "08:35:00", type = "string")
    private LocalTime arrivalTime;

    @Schema(description = "Total Distance (in km)", example = "1384.0")
    private Double totalDistanceKm;

    @Schema(description = "Base Fare", example = "500.0")
    private Double baseFare;

    @Schema(description = "List of coaches attached to this train")
    private List<CoachResponseDto> coaches;

    public TrainResponseDto() {
    }

    public TrainResponseDto(Long id, String trainNumber, String trainName, TrainType trainType,
                            StationResponseDto sourceStation, StationResponseDto destinationStation,
                            LocalTime departureTime, LocalTime arrivalTime,
                            Double totalDistanceKm, Double baseFare, List<CoachResponseDto> coaches) {
        this.id = id;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.trainType = trainType;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalDistanceKm = totalDistanceKm;
        this.baseFare = baseFare;
        this.coaches = coaches;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public StationResponseDto getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(StationResponseDto sourceStation) {
        this.sourceStation = sourceStation;
    }

    public StationResponseDto getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(StationResponseDto destinationStation) {
        this.destinationStation = destinationStation;
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

    public List<CoachResponseDto> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<CoachResponseDto> coaches) {
        this.coaches = coaches;
    }
}
