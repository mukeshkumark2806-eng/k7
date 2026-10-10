package com.railway.reservation.controller;

import com.railway.reservation.dto.request.StationRequestDto;
import com.railway.reservation.dto.response.StationResponseDto;
import com.railway.reservation.service.StationService;
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
@RequestMapping("/api/stations")
@Tag(name = "1. Station Management", description = "APIs for managing railway stations (NDLS, CSTM, SBC, etc.)")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    @Operation(summary = "Create a new railway station", description = "Registers a new station with a unique station code, name, city, and state.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Station successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Station code already exists")
    })
    public ResponseEntity<StationResponseDto> createStation(@Valid @RequestBody StationRequestDto requestDto) {
        StationResponseDto created = stationService.createStation(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all railway stations", description = "Retrieves a list of all railway stations registered in the system.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved stations list")
    public ResponseEntity<List<StationResponseDto>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get station by ID", description = "Retrieves railway station details by its database ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station found"),
            @ApiResponse(responseCode = "404", description = "Station not found")
    })
    public ResponseEntity<StationResponseDto> getStationById(
            @Parameter(description = "Station ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(stationService.getStationById(id));
    }

    @GetMapping("/code/{stationCode}")
    @Operation(summary = "Get station by Station Code", description = "Retrieves railway station details by its unique station code (e.g. NDLS).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station found"),
            @ApiResponse(responseCode = "404", description = "Station not found")
    })
    public ResponseEntity<StationResponseDto> getStationByCode(
            @Parameter(description = "Station code", example = "NDLS") @PathVariable String stationCode) {
        return ResponseEntity.ok(stationService.getStationByCode(stationCode));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing station", description = "Updates details for an existing station by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Station not found"),
            @ApiResponse(responseCode = "409", description = "Station code conflict")
    })
    public ResponseEntity<StationResponseDto> updateStation(
            @Parameter(description = "Station ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody StationRequestDto requestDto) {
        return ResponseEntity.ok(stationService.updateStation(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a station", description = "Deletes a station from the system by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Station successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Station not found")
    })
    public ResponseEntity<Void> deleteStation(
            @Parameter(description = "Station ID", example = "1") @PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }
}
