package com.railway.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Station creation and update request payload")
public class StationRequestDto {

    @NotBlank(message = "Station code cannot be empty")
    @Size(min = 2, max = 10, message = "Station code must be between 2 and 10 characters")
    @Schema(description = "Unique code for the station (e.g. NDLS)", example = "NDLS")
    private String stationCode;

    @NotBlank(message = "Station name cannot be empty")
    @Size(max = 100, message = "Station name must not exceed 100 characters")
    @Schema(description = "Full name of the railway station", example = "New Delhi Railway Station")
    private String stationName;

    @NotBlank(message = "City cannot be empty")
    @Size(max = 100, message = "City must not exceed 100 characters")
    @Schema(description = "City where the station is located", example = "New Delhi")
    private String city;

    @NotBlank(message = "State cannot be empty")
    @Size(max = 100, message = "State must not exceed 100 characters")
    @Schema(description = "State where the station is located", example = "Delhi")
    private String state;

    public StationRequestDto() {
    }

    public StationRequestDto(String stationCode, String stationName, String city, String state) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.city = city;
        this.state = state;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
