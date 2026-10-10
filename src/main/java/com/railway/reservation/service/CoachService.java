package com.railway.reservation.service;

import com.railway.reservation.dto.request.CoachRequestDto;
import com.railway.reservation.dto.response.CoachResponseDto;

import java.util.List;

public interface CoachService {
    CoachResponseDto createCoach(CoachRequestDto requestDto);
    List<CoachResponseDto> getAllCoaches();
    CoachResponseDto getCoachById(Long id);
    List<CoachResponseDto> getCoachesByTrainId(Long trainId);
    CoachResponseDto updateCoach(Long id, CoachRequestDto requestDto);
    void deleteCoach(Long id);
}
