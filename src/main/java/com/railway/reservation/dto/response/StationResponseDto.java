package com.railway.reservation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Station details response")
public class StationResponseDto {

    @Schema(description = "Station ID", example = "1")
    private Long id;

    @Schema(description = "Unique Station Code", example = "NDLS")
    private String stationCode;

    @Schema(description = "Station Name", example = "New Delhi Railway Station")
    private String stationName;

    @Schema(description = "City", example = "New Delhi")
    private String city;

    @Schema(description = "State", example = "Delhi")
    private String state;

    public StationResponseDto() {
    }

    public StationResponseDto(Long id, String stationCode, String stationName, String city, String state) {
        this.id = id;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
