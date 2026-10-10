package com.railway.reservation.service;

import com.railway.reservation.dto.request.PassengerRequestDto;
import com.railway.reservation.dto.response.PassengerResponseDto;

import java.util.List;

public interface PassengerService {
    PassengerResponseDto createPassenger(PassengerRequestDto requestDto);
    List<PassengerResponseDto> getAllPassengers();
    PassengerResponseDto getPassengerById(Long id);
    PassengerResponseDto getPassengerByEmail(String email);
    PassengerResponseDto updatePassenger(Long id, PassengerRequestDto requestDto);
    void deletePassenger(Long id);
}
