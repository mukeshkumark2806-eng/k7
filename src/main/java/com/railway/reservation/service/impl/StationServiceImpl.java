package com.railway.reservation.service.impl;

import com.railway.reservation.dto.request.StationRequestDto;
import com.railway.reservation.dto.response.StationResponseDto;
import com.railway.reservation.entity.Station;
import com.railway.reservation.exception.DuplicateResourceException;
import com.railway.reservation.exception.ResourceNotFoundException;
import com.railway.reservation.repository.StationRepository;
import com.railway.reservation.service.StationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public StationResponseDto createStation(StationRequestDto requestDto) {
        String code = requestDto.getStationCode().trim().toUpperCase();
        if (stationRepository.existsByStationCodeIgnoreCase(code)) {
            throw new DuplicateResourceException("Station with code '" + code + "' already exists");
        }

        Station station = new Station(
                code,
                requestDto.getStationName().trim(),
                requestDto.getCity().trim(),
                requestDto.getState().trim()
        );

        Station saved = stationRepository.save(station);
        return mapToResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StationResponseDto> getAllStations() {
        return stationRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StationResponseDto getStationById(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with id: " + id));
        return mapToResponseDto(station);
    }

    @Override
    @Transactional(readOnly = true)
    public StationResponseDto getStationByCode(String stationCode) {
        Station station = stationRepository.findByStationCodeIgnoreCase(stationCode.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with code: " + stationCode));
        return mapToResponseDto(station);
    }

    @Override
    public StationResponseDto updateStation(Long id, StationRequestDto requestDto) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with id: " + id));

        String newCode = requestDto.getStationCode().trim().toUpperCase();
        if (!station.getStationCode().equalsIgnoreCase(newCode) &&
                stationRepository.existsByStationCodeIgnoreCase(newCode)) {
            throw new DuplicateResourceException("Station with code '" + newCode + "' already exists");
        }

        station.setStationCode(newCode);
        station.setStationName(requestDto.getStationName().trim());
        station.setCity(requestDto.getCity().trim());
        station.setState(requestDto.getState().trim());

        Station updated = stationRepository.save(station);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteStation(Long id) {
        if (!stationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Station not found with id: " + id);
        }
        stationRepository.deleteById(id);
    }

    private StationResponseDto mapToResponseDto(Station station) {
        return new StationResponseDto(
                station.getId(),
                station.getStationCode(),
                station.getStationName(),
                station.getCity(),
                station.getState()
        );
    }
}
