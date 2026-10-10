package com.railway.reservation.controller;

import com.railway.reservation.dto.request.TrainRequestDto;
import com.railway.reservation.dto.response.TrainResponseDto;
import com.railway.reservation.service.TrainService;
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
@RequestMapping("/api/trains")
@Tag(name = "3. Train Management", description = "APIs for managing trains, source/destination routes, and schedules")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping
    @Operation(summary = "Create a new train", description = "Registers a new train with route, stations, schedule, and fare details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Train created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or identical stations"),
            @ApiResponse(responseCode = "404", description = "Source or destination station not found"),
            @ApiResponse(responseCode = "409", description = "Train number already exists")
    })
    public ResponseEntity<TrainResponseDto> createTrain(@Valid @RequestBody TrainRequestDto requestDto) {
        TrainResponseDto created = trainService.createTrain(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all trains", description = "Retrieves all trains currently registered along with their stations and coaches.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved train list")
    public ResponseEntity<List<TrainResponseDto>> getAllTrains() {
        return ResponseEntity.ok(trainService.getAllTrains());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get train by ID", description = "Retrieves train details including route and coach list by train ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Train found"),
            @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<TrainResponseDto> getTrainById(
            @Parameter(description = "Train ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(trainService.getTrainById(id));
    }

    @GetMapping("/number/{trainNumber}")
    @Operation(summary = "Get train by Train Number", description = "Retrieves train details by unique train number (e.g. 12951).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Train found"),
            @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<TrainResponseDto> getTrainByNumber(
            @Parameter(description = "Train Number", example = "12951") @PathVariable String trainNumber) {
        return ResponseEntity.ok(trainService.getTrainByNumber(trainNumber));
    }

    @GetMapping("/search")
    @Operation(summary = "Search trains between stations", description = "Finds available trains running directly between source and destination stations.")
    @ApiResponse(responseCode = "200", description = "Search results returned")
    public ResponseEntity<List<TrainResponseDto>> searchTrains(
            @Parameter(description = "Source Station ID", example = "1") @RequestParam Long sourceStationId,
            @Parameter(description = "Destination Station ID", example = "2") @RequestParam Long destinationStationId) {
        return ResponseEntity.ok(trainService.searchTrains(sourceStationId, destinationStationId));
    }

    @GetMapping("/search/query")
    @Operation(summary = "Search trains by Name or Number", description = "Finds trains matching a query string in train name or train number.")
    @ApiResponse(responseCode = "200", description = "Search results returned")
    public ResponseEntity<List<TrainResponseDto>> searchByNameOrNumber(
            @Parameter(description = "Query string", example = "Rajdhani") @RequestParam String q) {
        return ResponseEntity.ok(trainService.searchByNameOrNumber(q));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update train details", description = "Updates details for an existing train.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Train updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Train or station not found"),
            @ApiResponse(responseCode = "409", description = "Train number conflict")
    })
    public ResponseEntity<TrainResponseDto> updateTrain(
            @Parameter(description = "Train ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody TrainRequestDto requestDto) {
        return ResponseEntity.ok(trainService.updateTrain(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a train", description = "Removes a train and associated coaches from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Train deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<Void> deleteTrain(
            @Parameter(description = "Train ID", example = "1") @PathVariable Long id) {
        trainService.deleteTrain(id);
        return ResponseEntity.noContent().build();
    }
}
