package com.railway.reservation.repository;

import com.railway.reservation.entity.Booking;
import com.railway.reservation.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByPnrNumber(String pnrNumber);
    List<Booking> findByPassengerId(Long passengerId);
    List<Booking> findByTrainId(Long trainId);
    List<Booking> findByCoachIdAndTravelDate(Long coachId, LocalDate travelDate);
    long countByCoachIdAndTravelDateAndBookingStatus(Long coachId, LocalDate travelDate, BookingStatus status);
}
