package com.railway.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Ticket booking request payload")
public class BookingRequestDto {

    @NotNull(message = "Passenger ID is required")
    @Schema(description = "ID of passenger booking the ticket", example = "1")
    private Long passengerId;

    @NotNull(message = "Train ID is required")
    @Schema(description = "ID of train to travel on", example = "1")
    private Long trainId;

    @NotNull(message = "Coach ID is required")
    @Schema(description = "ID of selected coach", example = "1")
    private Long coachId;

    @NotNull(message = "Source station ID is required")
    @Schema(description = "Boarding station ID", example = "1")
    private Long sourceStationId;

    @NotNull(message = "Destination station ID is required")
    @Schema(description = "Destination station ID", example = "2")
    private Long destinationStationId;

    @NotNull(message = "Travel date is required")
    @FutureOrPresent(message = "Travel date cannot be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date of travel (yyyy-MM-dd)", example = "2026-10-15")
    private LocalDate travelDate;

    public BookingRequestDto() {
    }

    public BookingRequestDto(Long passengerId, Long trainId, Long coachId,
                             Long sourceStationId, Long destinationStationId, LocalDate travelDate) {
        this.passengerId = passengerId;
        this.trainId = trainId;
        this.coachId = coachId;
        this.sourceStationId = sourceStationId;
        this.destinationStationId = destinationStationId;
        this.travelDate = travelDate;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
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

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }
}
