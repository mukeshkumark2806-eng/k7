package com.railway.reservation.service;

import com.railway.reservation.dto.request.TrainRequestDto;
import com.railway.reservation.dto.response.TrainResponseDto;

import java.util.List;

public interface TrainService {
    TrainResponseDto createTrain(TrainRequestDto requestDto);
    List<TrainResponseDto> getAllTrains();
    TrainResponseDto getTrainById(Long id);
    TrainResponseDto getTrainByNumber(String trainNumber);
    List<TrainResponseDto> searchTrains(Long sourceStationId, Long destinationStationId);
    List<TrainResponseDto> searchByNameOrNumber(String query);
    TrainResponseDto updateTrain(Long id, TrainRequestDto requestDto);
    void deleteTrain(Long id);
}
