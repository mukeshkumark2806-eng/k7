package com.railway.reservation.service.impl;

import com.railway.reservation.dto.request.PassengerRequestDto;
import com.railway.reservation.dto.response.PassengerResponseDto;
import com.railway.reservation.entity.Passenger;
import com.railway.reservation.exception.DuplicateResourceException;
import com.railway.reservation.exception.ResourceNotFoundException;
import com.railway.reservation.repository.PassengerRepository;
import com.railway.reservation.service.PassengerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public PassengerResponseDto createPassenger(PassengerRequestDto requestDto) {
        String email = requestDto.getEmail().trim().toLowerCase();
        if (passengerRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException("Passenger with email '" + email + "' already exists");
        }

        Passenger passenger = new Passenger(
                requestDto.getFirstName().trim(),
                requestDto.getLastName().trim(),
                email,
                requestDto.getPhoneNumber().trim(),
                requestDto.getGender(),
                requestDto.getAge(),
                requestDto.getAddress() != null ? requestDto.getAddress().trim() : null
        );

        Passenger saved = passengerRepository.save(passenger);
        return mapToResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponseDto> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponseDto getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));
        return mapToResponseDto(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponseDto getPassengerByEmail(String email) {
        Passenger passenger = passengerRepository.findByEmailIgnoreCase(email.trim().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with email: " + email));
        return mapToResponseDto(passenger);
    }

    @Override
    public PassengerResponseDto updatePassenger(Long id, PassengerRequestDto requestDto) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));

        String newEmail = requestDto.getEmail().trim().toLowerCase();
        if (!passenger.getEmail().equalsIgnoreCase(newEmail) &&
                passengerRepository.existsByEmailIgnoreCase(newEmail)) {
            throw new DuplicateResourceException("Passenger with email '" + newEmail + "' already exists");
        }

        passenger.setFirstName(requestDto.getFirstName().trim());
        passenger.setLastName(requestDto.getLastName().trim());
        passenger.setEmail(newEmail);
        passenger.setPhoneNumber(requestDto.getPhoneNumber().trim());
        passenger.setGender(requestDto.getGender());
        passenger.setAge(requestDto.getAge());
        passenger.setAddress(requestDto.getAddress() != null ? requestDto.getAddress().trim() : null);

        Passenger updated = passengerRepository.save(passenger);
        return mapToResponseDto(updated);
    }

    @Override
    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Passenger not found with id: " + id);
        }
        passengerRepository.deleteById(id);
    }

    private PassengerResponseDto mapToResponseDto(Passenger passenger) {
        return new PassengerResponseDto(
                passenger.getId(),
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getEmail(),
                passenger.getPhoneNumber(),
                passenger.getGender(),
                passenger.getAge(),
                passenger.getAddress()
        );
    }
}
