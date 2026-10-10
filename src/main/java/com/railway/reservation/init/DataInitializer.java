package com.railway.reservation.init;

import com.railway.reservation.entity.*;
import com.railway.reservation.entity.enums.BookingStatus;
import com.railway.reservation.entity.enums.CoachType;
import com.railway.reservation.entity.enums.Gender;
import com.railway.reservation.entity.enums.TrainType;
import com.railway.reservation.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final StationRepository stationRepository;
    private final TrainRepository trainRepository;
    private final CoachRepository coachRepository;
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    public DataInitializer(StationRepository stationRepository,
                           TrainRepository trainRepository,
                           CoachRepository coachRepository,
                           PassengerRepository passengerRepository,
                           BookingRepository bookingRepository) {
        this.stationRepository = stationRepository;
        this.trainRepository = trainRepository;
        this.coachRepository = coachRepository;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) {
        if (stationRepository.count() > 0) {
            log.info("Database already initialized. Skipping sample data population.");
            return;
        }

        log.info("Populating initial Railway Reservation sample data...");

        // 1. Stations
        Station ndls = stationRepository.save(new Station("NDLS", "New Delhi Railway Station", "New Delhi", "Delhi"));
        Station mmct = stationRepository.save(new Station("MMCT", "Mumbai Central Railway Station", "Mumbai", "Maharashtra"));
        Station hwh = stationRepository.save(new Station("HWH", "Howrah Junction", "Kolkata", "West Bengal"));
        Station sbc = stationRepository.save(new Station("SBC", "KSR Bengaluru City Junction", "Bengaluru", "Karnataka"));
        Station mas = stationRepository.save(new Station("MAS", "Chennai Central", "Chennai", "Tamil Nadu"));

        // 2. Trains
        Train rajdhaniMumbai = trainRepository.save(new Train(
                "12951",
                "Mumbai Rajdhani Express",
                TrainType.RAJDHANI,
                ndls,
                mmct,
                LocalTime.of(16, 55),
                LocalTime.of(8, 35),
                1384.0,
                800.0
        ));

        Train rajdhaniHowrah = trainRepository.save(new Train(
                "12302",
                "Kolkata Rajdhani Express",
                TrainType.RAJDHANI,
                ndls,
                hwh,
                LocalTime.of(16, 50),
                LocalTime.of(9, 55),
                1450.0,
                850.0
        ));

        Train karnatakaExp = trainRepository.save(new Train(
                "12628",
                "Karnataka Express",
                TrainType.SUPERFAST,
                ndls,
                sbc,
                LocalTime.of(20, 20),
                LocalTime.of(12, 0),
                2400.0,
                900.0
        ));

        Train vandeBharat = trainRepository.save(new Train(
                "20608",
                "Vande Bharat Express",
                TrainType.VANDE_BHARAT,
                mas,
                sbc,
                LocalTime.of(5, 50),
                LocalTime.of(10, 10),
                360.0,
                450.0
        ));

        // 3. Coaches
        Coach rjH1 = coachRepository.save(new Coach("H1", CoachType.AC_FIRST_CLASS, 24, rajdhaniMumbai));
        Coach rjA1 = coachRepository.save(new Coach("A1", CoachType.AC_2_TIER, 48, rajdhaniMumbai));
        Coach rjB1 = coachRepository.save(new Coach("B1", CoachType.AC_3_TIER, 72, rajdhaniMumbai));
        Coach rjB2 = coachRepository.save(new Coach("B2", CoachType.AC_3_TIER, 72, rajdhaniMumbai));

        coachRepository.save(new Coach("A1", CoachType.AC_2_TIER, 48, rajdhaniHowrah));
        coachRepository.save(new Coach("B1", CoachType.AC_3_TIER, 72, rajdhaniHowrah));

        Coach krS1 = coachRepository.save(new Coach("S1", CoachType.SLEEPER, 72, karnatakaExp));
        Coach krS2 = coachRepository.save(new Coach("S2", CoachType.SLEEPER, 72, karnatakaExp));
        coachRepository.save(new Coach("B1", CoachType.AC_3_TIER, 72, karnatakaExp));

        Coach vbC1 = coachRepository.save(new Coach("C1", CoachType.CHAIR_CAR, 78, vandeBharat));
        coachRepository.save(new Coach("E1", CoachType.AC_FIRST_CLASS, 52, vandeBharat));

        // 4. Passengers
        Passenger rahul = passengerRepository.save(new Passenger(
                "Rahul",
                "Sharma",
                "rahul.sharma@example.com",
                "9876543210",
                Gender.MALE,
                28,
                "B-42 Connaught Place, New Delhi"
        ));

        Passenger priya = passengerRepository.save(new Passenger(
                "Priya",
                "Patel",
                "priya.patel@example.com",
                "9876543211",
                Gender.FEMALE,
                25,
                "15 Marine Drive, Mumbai"
        ));

        Passenger amit = passengerRepository.save(new Passenger(
                "Amit",
                "Verma",
                "amit.verma@example.com",
                "9876543212",
                Gender.MALE,
                34,
                "Sector 62, Noida, Uttar Pradesh"
        ));

        Passenger sneha = passengerRepository.save(new Passenger(
                "Sneha",
                "Reddy",
                "sneha.reddy@example.com",
                "9876543213",
                Gender.FEMALE,
                30,
                "Indiranagar, Bengaluru, Karnataka"
        ));

        // 5. Bookings
        LocalDate travelDate1 = LocalDate.now().plusDays(3);
        double fare1 = calculateFare(rajdhaniMumbai.getBaseFare(), rjB1.getCoachType().getFareMultiplier(), rajdhaniMumbai.getTotalDistanceKm());
        bookingRepository.save(new Booking(
                "PNR2610101001",
                LocalDateTime.now().minusHours(2),
                travelDate1,
                rahul,
                rajdhaniMumbai,
                rjB1,
                ndls,
                mmct,
                "B1-01",
                fare1,
                BookingStatus.CONFIRMED
        ));

        LocalDate travelDate2 = LocalDate.now().plusDays(5);
        double fare2 = calculateFare(karnatakaExp.getBaseFare(), krS1.getCoachType().getFareMultiplier(), karnatakaExp.getTotalDistanceKm());
        bookingRepository.save(new Booking(
                "PNR2610101002",
                LocalDateTime.now().minusHours(1),
                travelDate2,
                priya,
                karnatakaExp,
                krS1,
                ndls,
                sbc,
                "S1-01",
                fare2,
                BookingStatus.CONFIRMED
        ));

        LocalDate travelDate3 = LocalDate.now().plusDays(7);
        double fare3 = calculateFare(vandeBharat.getBaseFare(), vbC1.getCoachType().getFareMultiplier(), vandeBharat.getTotalDistanceKm());
        bookingRepository.save(new Booking(
                "PNR2610101003",
                LocalDateTime.now().minusMinutes(30),
                travelDate3,
                sneha,
                vandeBharat,
                vbC1,
                mas,
                sbc,
                "C1-01",
                fare3,
                BookingStatus.CONFIRMED
        ));

        log.info("Railway Reservation sample data population completed successfully!");
    }

    private double calculateFare(double baseFare, double multiplier, double distanceKm) {
        double fare = baseFare * multiplier * (distanceKm / 500.0);
        return BigDecimal.valueOf(fare).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
