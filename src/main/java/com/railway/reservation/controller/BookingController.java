package com.railway.reservation.controller;

import com.railway.reservation.dto.request.BookingRequestDto;
import com.railway.reservation.dto.response.BookingResponseDto;
import com.railway.reservation.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "5. Booking Management", description = "APIs for booking train tickets, checking PNR status, and cancellations")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(summary = "Book a train ticket", description = "Books a ticket for a passenger on a specific train, coach, travel date, and stations. Automatically generates PNR, assigns seat, and calculates fare.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket successfully booked"),
            @ApiResponse(responseCode = "400", description = "Invalid booking details or coach fully booked"),
            @ApiResponse(responseCode = "404", description = "Passenger, train, coach, or station not found")
    })
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingRequestDto requestDto) {
        BookingResponseDto created = bookingService.createBooking(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieves all bookings made across the entire system.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved bookings list")
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieves booking details by booking ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<BookingResponseDto> getBookingById(
            @Parameter(description = "Booking ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/pnr/{pnrNumber}")
    @Operation(summary = "Get booking by PNR Number", description = "Lookup booking and ticket status by 10-digit PNR number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PNR details found"),
            @ApiResponse(responseCode = "404", description = "PNR not found")
    })
    public ResponseEntity<BookingResponseDto> getBookingByPnr(
            @Parameter(description = "PNR Number", example = "PNR2610101234") @PathVariable String pnrNumber) {
        return ResponseEntity.ok(bookingService.getBookingByPnr(pnrNumber));
    }

    @GetMapping("/passenger/{passengerId}")
    @Operation(summary = "Get bookings by Passenger ID", description = "Retrieves all bookings made by a particular passenger.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<List<BookingResponseDto>> getBookingsByPassengerId(
            @Parameter(description = "Passenger ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingsByPassengerId(id));
    }

    @GetMapping("/train/{trainId}")
    @Operation(summary = "Get bookings by Train ID", description = "Retrieves all bookings associated with a specific train.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved"),
            @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<List<BookingResponseDto>> getBookingsByTrainId(
            @Parameter(description = "Train ID", example = "1") @PathVariable Long trainId) {
        return ResponseEntity.ok(bookingService.getBookingsByTrainId(trainId));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel a booking", description = "Cancels an active booking and marks its status as CANCELLED.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Booking is already cancelled"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<BookingResponseDto> cancelBooking(
            @Parameter(description = "Booking ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a booking record", description = "Permanently deletes a booking record from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "Booking ID", example = "1") @PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
