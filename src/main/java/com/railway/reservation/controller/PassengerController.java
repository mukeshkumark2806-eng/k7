package com.railway.reservation.controller;

import com.railway.reservation.dto.request.PassengerRequestDto;
import com.railway.reservation.dto.response.BookingResponseDto;
import com.railway.reservation.dto.response.PassengerResponseDto;
import com.railway.reservation.service.BookingService;
import com.railway.reservation.service.PassengerService;
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
@RequestMapping("/api/passengers")
@Tag(name = "4. Passenger Management", description = "APIs for managing passenger accounts, personal details, and bookings")
public class PassengerController {

    private final PassengerService passengerService;
    private final BookingService bookingService;

    public PassengerController(PassengerService passengerService, BookingService bookingService) {
        this.passengerService = passengerService;
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(summary = "Register a new passenger", description = "Creates a new passenger profile with contact and personal details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid passenger data"),
            @ApiResponse(responseCode = "409", description = "Email already registered")
    })
    public ResponseEntity<PassengerResponseDto> createPassenger(@Valid @RequestBody PassengerRequestDto requestDto) {
        PassengerResponseDto created = passengerService.createPassenger(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all passengers", description = "Retrieves all passengers registered in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved passengers list")
    public ResponseEntity<List<PassengerResponseDto>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get passenger by ID", description = "Retrieves passenger profile details by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger found"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<PassengerResponseDto> getPassengerById(
            @Parameter(description = "Passenger ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get passenger by Email", description = "Retrieves passenger profile details by unique email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger found"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<PassengerResponseDto> getPassengerByEmail(
            @Parameter(description = "Passenger email", example = "rahul.sharma@example.com") @PathVariable String email) {
        return ResponseEntity.ok(passengerService.getPassengerByEmail(email));
    }

    @GetMapping("/{id}/bookings")
    @Operation(summary = "Get passenger's booking history", description = "Retrieves all booking transactions and tickets for a specific passenger.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<List<BookingResponseDto>> getPassengerBookings(
            @Parameter(description = "Passenger ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingsByPassengerId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update passenger profile", description = "Updates passenger profile details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "409", description = "Email conflict")
    })
    public ResponseEntity<PassengerResponseDto> updatePassenger(
            @Parameter(description = "Passenger ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody PassengerRequestDto requestDto) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete passenger profile", description = "Removes a passenger profile and their bookings from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Passenger deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<Void> deletePassenger(
            @Parameter(description = "Passenger ID", example = "1") @PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
