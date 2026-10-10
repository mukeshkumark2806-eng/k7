package com.railway.reservation.dto.request;

import com.railway.reservation.entity.enums.CoachType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Coach creation and update request payload")
public class CoachRequestDto {

    @NotBlank(message = "Coach number cannot be empty")
    @Size(min = 1, max = 10, message = "Coach number must be between 1 and 10 characters")
    @Schema(description = "Coach identifier/number (e.g. B1, A1, S1)", example = "B1")
    private String coachNumber;

    @NotNull(message = "Coach type is required")
    @Schema(description = "Coach class type", example = "AC_3_TIER")
    private CoachType coachType;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    @Schema(description = "Total seating capacity in this coach", example = "72")
    private Integer totalSeats;

    @NotNull(message = "Train ID is required")
    @Schema(description = "ID of the train this coach belongs to", example = "1")
    private Long trainId;

    public CoachRequestDto() {
    }

    public CoachRequestDto(String coachNumber, CoachType coachType, Integer totalSeats, Long trainId) {
        this.coachNumber = coachNumber;
        this.coachType = coachType;
        this.totalSeats = totalSeats;
        this.trainId = trainId;
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
}
