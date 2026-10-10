package com.railway.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railway.reservation.dto.request.*;
import com.railway.reservation.entity.enums.CoachType;
import com.railway.reservation.entity.enums.Gender;
import com.railway.reservation.entity.enums.TrainType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RailwayReservationSystemApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Context loads and pre-seeded stations are retrievable")
    void testGetAllStations() throws Exception {
        mockMvc.perform(get("/api/stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(4))))
                .andExpect(jsonPath("$[0].stationCode", notNullValue()));
    }

    @Test
    @DisplayName("Get station by code NDLS")
    void testGetStationByCode() throws Exception {
        mockMvc.perform(get("/api/stations/code/NDLS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationCode", is("NDLS")))
                .andExpect(jsonPath("$.city", is("New Delhi")));
    }

    @Test
    @DisplayName("Create, update, and delete station lifecycle")
    void testStationLifecycle() throws Exception {
        StationRequestDto newStation = new StationRequestDto("PNBE", "Patna Junction", "Patna", "Bihar");

        String response = mockMvc.perform(post("/api/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stationCode", is("PNBE")))
                .andReturn().getResponse().getContentAsString();

        Long stationId = objectMapper.readTree(response).get("id").asLong();

        // Update
        StationRequestDto updatedStation = new StationRequestDto("PNBE", "Patna Central Junction", "Patna", "Bihar");
        mockMvc.perform(put("/api/stations/" + stationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationName", is("Patna Central Junction")));

        // Delete
        mockMvc.perform(delete("/api/stations/" + stationId))
                .andExpect(status().isNoContent());

        // Verify deleted
        mockMvc.perform(get("/api/stations/" + stationId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get all trains and search by route")
    void testGetTrainsAndSearch() throws Exception {
        mockMvc.perform(get("/api/trains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].trainNumber", notNullValue()))
                .andExpect(jsonPath("$[0].sourceStation", notNullValue()))
                .andExpect(jsonPath("$[0].coaches", notNullValue()));

        mockMvc.perform(get("/api/trains/search/query?q=Rajdhani"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    @DisplayName("Get all coaches and filter by train")
    void testGetCoaches() throws Exception {
        mockMvc.perform(get("/api/coaches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))));

        mockMvc.perform(get("/api/coaches/train/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Create passenger and fetch details")
    void testPassengerOperations() throws Exception {
        PassengerRequestDto passenger = new PassengerRequestDto(
                "Vikram", "Singhania", "vikram.singh@example.com",
                "9811223344", Gender.MALE, 32, "Civil Lines, Jaipur"
        );

        mockMvc.perform(post("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passenger)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("vikram.singh@example.com")));

        mockMvc.perform(get("/api/passengers/email/vikram.singh@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Vikram")));
    }

    @Test
    @DisplayName("Book ticket, verify PNR, and cancel booking")
    void testBookingLifecycle() throws Exception {
        BookingRequestDto bookingRequest = new BookingRequestDto(
                1L, 1L, 1L, 1L, 2L, LocalDate.now().plusDays(10)
        );

        String response = mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pnrNumber", startsWith("PNR")))
                .andExpect(jsonPath("$.bookingStatus", is("CONFIRMED")))
                .andExpect(jsonPath("$.fareAmount", greaterThan(0.0)))
                .andExpect(jsonPath("$.seatNumber", notNullValue()))
                .andReturn().getResponse().getContentAsString();

        String pnr = objectMapper.readTree(response).get("pnrNumber").asText();
        Long bookingId = objectMapper.readTree(response).get("id").asLong();

        // Retrieve by PNR
        mockMvc.perform(get("/api/bookings/pnr/" + pnr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pnrNumber", is(pnr)));

        // Cancel booking
        mockMvc.perform(put("/api/bookings/" + bookingId + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingStatus", is("CANCELLED")));
    }

    @Test
    @DisplayName("Swagger OpenAPI spec endpoint is available")
    void testSwaggerEndpoints() throws Exception {
        mockMvc.perform(get("/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.openapi", notNullValue()))
                .andExpect(jsonPath("$.info.title", is("Railway Reservation System REST API")));
    }
}
