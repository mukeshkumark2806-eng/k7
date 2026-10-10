package com.railway.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.railway.reservation.entity.enums.BookingStatus;
import com.railway.reservation.entity.enums.CoachType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Booking confirmation details response")
public class BookingResponseDto {

    @Schema(description = "Booking ID", example = "1")
    private Long id;

    @Schema(description = "Unique 10-digit PNR Number", example = "PNR2610101234")
    private String pnrNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Timestamp when booking was made", example = "2026-10-10 14:30:00")
    private LocalDateTime bookingDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date of travel", example = "2026-10-15")
    private LocalDate travelDate;

    @Schema(description = "Passenger summary")
    private PassengerResponseDto passenger;

    @Schema(description = "Train ID", example = "1")
    private Long trainId;

    @Schema(description = "Train Number", example = "12951")
    private String trainNumber;

    @Schema(description = "Train Name", example = "Mumbai Rajdhani Express")
    private String trainName;

    @Schema(description = "Coach ID", example = "1")
    private Long coachId;

    @Schema(description = "Coach Number", example = "B1")
    private String coachNumber;

    @Schema(description = "Coach Type", example = "AC_3_TIER")
    private CoachType coachType;

    @Schema(description = "Boarding Station Code", example = "NDLS")
    private String sourceStationCode;

    @Schema(description = "Boarding Station Name", example = "New Delhi Railway Station")
    private String sourceStationName;

    @Schema(description = "Destination Station Code", example = "MMCT")
    private String destinationStationCode;

    @Schema(description = "Destination Station Name", example = "Mumbai Central Railway Station")
    private String destinationStationName;

    @Schema(description = "Allocated Seat Number", example = "B1-01")
    private String seatNumber;

    @Schema(description = "Calculated Fare Amount", example = "1550.0")
    private Double fareAmount;

    @Schema(description = "Booking Status", example = "CONFIRMED")
    private BookingStatus bookingStatus;

    public BookingResponseDto() {
    }

    public BookingResponseDto(Long id, String pnrNumber, LocalDateTime bookingDate, LocalDate travelDate,
                              PassengerResponseDto passenger, Long trainId, String trainNumber,
                              String trainName, Long coachId, String coachNumber, CoachType coachType,
                              String sourceStationCode, String sourceStationName,
                              String destinationStationCode, String destinationStationName,
                              String seatNumber, Double fareAmount, BookingStatus bookingStatus) {
        this.id = id;
        this.pnrNumber = pnrNumber;
        this.bookingDate = bookingDate;
        this.travelDate = travelDate;
        this.passenger = passenger;
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.coachId = coachId;
        this.coachNumber = coachNumber;
        this.coachType = coachType;
        this.sourceStationCode = sourceStationCode;
        this.sourceStationName = sourceStationName;
        this.destinationStationCode = destinationStationCode;
        this.destinationStationName = destinationStationName;
        this.seatNumber = seatNumber;
        this.fareAmount = fareAmount;
        this.bookingStatus = bookingStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public PassengerResponseDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerResponseDto passenger) {
        this.passenger = passenger;
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

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
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

    public String getSourceStationCode() {
        return sourceStationCode;
    }

    public void setSourceStationCode(String sourceStationCode) {
        this.sourceStationCode = sourceStationCode;
    }

    public String getSourceStationName() {
        return sourceStationName;
    }

    public void setSourceStationName(String sourceStationName) {
        this.sourceStationName = sourceStationName;
    }

    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    public void setDestinationStationCode(String destinationStationCode) {
        this.destinationStationCode = destinationStationCode;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public void setDestinationStationName(String destinationStationName) {
        this.destinationStationName = destinationStationName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Double getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(Double fareAmount) {
        this.fareAmount = fareAmount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
