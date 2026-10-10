package com.railway.reservation.dto.response;

import com.railway.reservation.entity.enums.CoachType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Coach details response")
public class CoachResponseDto {

    @Schema(description = "Coach ID", example = "1")
    private Long id;

    @Schema(description = "Coach number", example = "B1")
    private String coachNumber;

    @Schema(description = "Coach type", example = "AC_3_TIER")
    private CoachType coachType;

    @Schema(description = "Total seats capacity", example = "72")
    private Integer totalSeats;

    @Schema(description = "Associated Train ID", example = "1")
    private Long trainId;

    @Schema(description = "Associated Train Number", example = "12951")
    private String trainNumber;

    @Schema(description = "Associated Train Name", example = "Mumbai Rajdhani Express")
    private String trainName;

    public CoachResponseDto() {
    }

    public CoachResponseDto(Long id, String coachNumber, CoachType coachType, Integer totalSeats,
                            Long trainId, String trainNumber, String trainName) {
        this.id = id;
        this.coachNumber = coachNumber;
        this.coachType = coachType;
        this.totalSeats = totalSeats;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(String coachNumber) {
        this.coachNumber = coachNumber;
    }

    public CoachType getCoachType() {
        return coachType;
    }

    public void setCoachType(CoachType coachType) {
        this.coachType = coachType;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
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
}
