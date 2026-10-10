package com.railway.reservation.controller;

import com.railway.reservation.dto.request.CoachRequestDto;
import com.railway.reservation.dto.response.CoachResponseDto;
import com.railway.reservation.service.CoachService;
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
@RequestMapping("/api/coaches")
@Tag(name = "2. Coach Management", description = "APIs for managing train coaches (AC 1st Class, 2-Tier, 3-Tier, Sleeper, etc.)")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @PostMapping
    @Operation(summary = "Add a coach to a train", description = "Attaches a new coach (with coach number, type, capacity) to an existing train.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coach successfully created and attached"),
            @ApiResponse(responseCode = "400", description = "Invalid coach data"),
            @ApiResponse(responseCode = "404", description = "Associated train not found"),
            @ApiResponse(responseCode = "409", description = "Coach number already exists on this train")
    })
    public ResponseEntity<CoachResponseDto> createCoach(@Valid @RequestBody CoachRequestDto requestDto) {
        CoachResponseDto created = coachService.createCoach(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all coaches", description = "Retrieves a list of all coaches across all trains.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved coaches list")
    public ResponseEntity<List<CoachResponseDto>> getAllCoaches() {
        return ResponseEntity.ok(coachService.getAllCoaches());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get coach by ID", description = "Retrieves coach details by coach ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coach found"),
            @ApiResponse(responseCode = "404", description = "Coach not found")
    })
    public ResponseEntity<CoachResponseDto> getCoachById(
            @Parameter(description = "Coach ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(coachService.getCoachById(id));
    }

    @GetMapping("/train/{trainId}")
    @Operation(summary = "Get all coaches of a train", description = "Retrieves all coaches attached to a specific train.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coaches retrieved"),
            @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<List<CoachResponseDto>> getCoachesByTrainId(
            @Parameter(description = "Train ID", example = "1") @PathVariable Long trainId) {
        return ResponseEntity.ok(coachService.getCoachesByTrainId(trainId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update coach details", description = "Updates details of an existing coach.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coach successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Coach or train not found"),
            @ApiResponse(responseCode = "409", description = "Coach number conflict")
    })
    public ResponseEntity<CoachResponseDto> updateCoach(
            @Parameter(description = "Coach ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody CoachRequestDto requestDto) {
        return ResponseEntity.ok(coachService.updateCoach(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a coach", description = "Removes a coach from its train and system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Coach successfully removed"),
            @ApiResponse(responseCode = "404", description = "Coach not found")
    })
    public ResponseEntity<Void> deleteCoach(
            @Parameter(description = "Coach ID", example = "1") @PathVariable Long id) {
        coachService.deleteCoach(id);
        return ResponseEntity.noContent().build();
    }
}
