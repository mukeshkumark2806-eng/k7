package com.railway.reservation.entity;

import com.railway.reservation.entity.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pnr_number", nullable = false, unique = true, length = 15)
    private String pnrNumber;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_station_id", nullable = false)
    private Station sourceStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private Station destinationStation;

    @Column(name = "seat_number", nullable = false, length = 20)
    private String seatNumber;

    @Column(name = "fare_amount", nullable = false)
    private Double fareAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false, length = 20)
    private BookingStatus bookingStatus;

    public Booking() {
    }

    public Booking(Long id, String pnrNumber, LocalDateTime bookingDate, LocalDate travelDate,
                   Passenger passenger, Train train, Coach coach, Station sourceStation,
                   Station destinationStation, String seatNumber, Double fareAmount,
                   BookingStatus bookingStatus) {
        this.id = id;
        this.pnrNumber = pnrNumber;
        this.bookingDate = bookingDate;
        this.travelDate = travelDate;
        this.passenger = passenger;
        this.train = train;
        this.coach = coach;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.seatNumber = seatNumber;
        this.fareAmount = fareAmount;
        this.bookingStatus = bookingStatus;
    }

    public Booking(String pnrNumber, LocalDateTime bookingDate, LocalDate travelDate,
                   Passenger passenger, Train train, Coach coach, Station sourceStation,
                   Station destinationStation, String seatNumber, Double fareAmount,
                   BookingStatus bookingStatus) {
        this.pnrNumber = pnrNumber;
        this.bookingDate = bookingDate;
        this.travelDate = travelDate;
        this.passenger = passenger;
        this.train = train;
        this.coach = coach;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
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

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(Station sourceStation) {
        this.sourceStation = sourceStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) || Objects.equals(pnrNumber, booking.pnrNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pnrNumber);
    }
}
