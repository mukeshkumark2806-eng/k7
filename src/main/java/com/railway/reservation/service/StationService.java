package com.railway.reservation.service;

import com.railway.reservation.dto.request.StationRequestDto;
import com.railway.reservation.dto.response.StationResponseDto;

import java.util.List;

public interface StationService {
    StationResponseDto createStation(StationRequestDto requestDto);
    List<StationResponseDto> getAllStations();
    StationResponseDto getStationById(Long id);
    StationResponseDto getStationByCode(String stationCode);
    StationResponseDto updateStation(Long id, StationRequestDto requestDto);
    void deleteStation(Long id);
}
