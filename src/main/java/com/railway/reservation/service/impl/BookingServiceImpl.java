package com.railway.reservation.service.impl;

import com.railway.reservation.dto.request.BookingRequestDto;
import com.railway.reservation.dto.response.BookingResponseDto;
import com.railway.reservation.dto.response.PassengerResponseDto;
import com.railway.reservation.entity.*;
import com.railway.reservation.entity.enums.BookingStatus;
import com.railway.reservation.exception.BookingException;
import com.railway.reservation.exception.ResourceNotFoundException;
import com.railway.reservation.repository.*;
import com.railway.reservation.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final TrainRepository trainRepository;
    private final CoachRepository coachRepository;
    private final StationRepository stationRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              PassengerRepository passengerRepository,
                              TrainRepository trainRepository,
                              CoachRepository coachRepository,
                              StationRepository stationRepository) {
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.trainRepository = trainRepository;
        this.coachRepository = coachRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public BookingResponseDto createBooking(BookingRequestDto requestDto) {
        Passenger passenger = passengerRepository.findById(requestDto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + requestDto.getPassengerId()));

        Train train = trainRepository.findById(requestDto.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + requestDto.getTrainId()));

        Coach coach = coachRepository.findById(requestDto.getCoachId())
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found with id: " + requestDto.getCoachId()));

        if (!coach.getTrain().getId().equals(train.getId())) {
            throw new BookingException("Coach '" + coach.getCoachNumber() + "' does not belong to Train '" + train.getTrainNumber() + "'");
        }

        Station sourceStation = stationRepository.findById(requestDto.getSourceStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Source station not found with id: " + requestDto.getSourceStationId()));

        Station destinationStation = stationRepository.findById(requestDto.getDestinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination station not found with id: " + requestDto.getDestinationStationId()));

        if (sourceStation.getId().equals(destinationStation.getId())) {
            throw new BookingException("Source station and destination station cannot be the same");
        }

        // Check coach capacity for travel date
        long activeBookings = bookingRepository.countByCoachIdAndTravelDateAndBookingStatus(
                coach.getId(), requestDto.getTravelDate(), BookingStatus.CONFIRMED);

        if (activeBookings >= coach.getTotalSeats()) {
            throw new BookingException("Coach " + coach.getCoachNumber() + " is fully booked for travel date " + requestDto.getTravelDate());
        }

        int seatNum = (int) activeBookings + 1;
        String assignedSeat = coach.getCoachNumber() + "-" + String.format("%02d", seatNum);

        // Calculate Fare: baseFare * coach multiplier * distance factor
        double calculatedFare = train.getBaseFare() * coach.getCoachType().getFareMultiplier() * (train.getTotalDistanceKm() / 500.0);
        double roundedFare = BigDecimal.valueOf(calculatedFare)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        // Generate PNR
        String pnr = generatePnr();

        Booking booking = new Booking(
                pnr,
                LocalDateTime.now(),
                requestDto.getTravelDate(),
                passenger,
                train,
                coach,
                sourceStation,
                destinationStation,
                assignedSeat,
                roundedFare,
                BookingStatus.CONFIRMED
        );

        Booking saved = bookingRepository.save(booking);
        return mapToResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return mapToResponseDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDto getBookingByPnr(String pnrNumber) {
        Booking booking = bookingRepository.findByPnrNumber(pnrNumber.trim().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnrNumber));
        return mapToResponseDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDto> getBookingsByPassengerId(Long passengerId) {
        if (!passengerRepository.existsById(passengerId)) {
            throw new ResourceNotFoundException("Passenger not found with id: " + passengerId);
        }
        return bookingRepository.findByPassengerId(passengerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDto> getBookingsByTrainId(Long trainId) {
        if (!trainRepository.existsById(trainId)) {
            throw new ResourceNotFoundException("Train not found with id: " + trainId);
        }
        return bookingRepository.findByTrainId(trainId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new BookingException("Booking is already cancelled");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    private String generatePnr() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
        String datePrefix = LocalDateTime.now().format(dtf);
        int randomDigits = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "PNR" + datePrefix + randomDigits;
    }

    private BookingResponseDto mapToResponseDto(Booking booking) {
        Passenger p = booking.getPassenger();
        PassengerResponseDto passengerDto = p != null ? new PassengerResponseDto(
                p.getId(), p.getFirstName(), p.getLastName(), p.getEmail(),
                p.getPhoneNumber(), p.getGender(), p.getAge(), p.getAddress()
        ) : null;

        Train t = booking.getTrain();
        Coach c = booking.getCoach();
        Station src = booking.getSourceStation();
        Station dest = booking.getDestinationStation();

        return new BookingResponseDto(
                booking.getId(),
                booking.getPnrNumber(),
                booking.getBookingDate(),
                booking.getTravelDate(),
                passengerDto,
                t != null ? t.getId() : null,
                t != null ? t.getTrainNumber() : null,
                t != null ? t.getTrainName() : null,
                c != null ? c.getId() : null,
                c != null ? c.getCoachNumber() : null,
                c != null ? c.getCoachType() : null,
                src != null ? src.getStationCode() : null,
                src != null ? src.getStationName() : null,
                dest != null ? dest.getStationCode() : null,
                dest != null ? dest.getStationName() : null,
                booking.getSeatNumber(),
                booking.getFareAmount(),
                booking.getBookingStatus()
        );
    }
}
