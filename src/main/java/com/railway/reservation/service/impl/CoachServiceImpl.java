package com.railway.reservation.service.impl;

import com.railway.reservation.dto.request.CoachRequestDto;
import com.railway.reservation.dto.response.CoachResponseDto;
import com.railway.reservation.entity.Coach;
import com.railway.reservation.entity.Train;
import com.railway.reservation.exception.DuplicateResourceException;
import com.railway.reservation.exception.ResourceNotFoundException;
import com.railway.reservation.repository.CoachRepository;
import com.railway.reservation.repository.TrainRepository;
import com.railway.reservation.service.CoachService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final TrainRepository trainRepository;

    public CoachServiceImpl(CoachRepository coachRepository, TrainRepository trainRepository) {
        this.coachRepository = coachRepository;
        this.trainRepository = trainRepository;
    }

    @Override
    public CoachResponseDto createCoach(CoachRequestDto requestDto) {
        Train train = trainRepository.findById(requestDto.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + requestDto.getTrainId()));

        String coachNum = requestDto.getCoachNumber().trim().toUpperCase();
        if (coachRepository.existsByTrainIdAndCoachNumberIgnoreCase(train.getId(), coachNum)) {
            throw new DuplicateResourceException("Coach '" + coachNum + "' already exists for Train " + train.getTrainNumber());
        }

        Coach coach = new Coach(
                coachNum,
                requestDto.getCoachType(),
                requestDto.getTotalSeats(),
                train
        );

        Coach saved = coachRepository.save(coach);
        return mapToResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoachResponseDto> getAllCoaches() {
        return coachRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CoachResponseDto getCoachById(Long id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found with id: " + id));
        return mapToResponseDto(coach);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoachResponseDto> getCoachesByTrainId(Long trainId) {
        if (!trainRepository.existsById(trainId)) {
            throw new ResourceNotFoundException("Train not found with id: " + trainId);
        }
        return coachRepository.findByTrainId(trainId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CoachResponseDto updateCoach(Long id, CoachRequestDto requestDto) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found with id: " + id));

        Train train = trainRepository.findById(requestDto.getTrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + requestDto.getTrainId()));

        String newCoachNum = requestDto.getCoachNumber().trim().toUpperCase();
        boolean numChangedOrTrainChanged = !coach.getCoachNumber().equalsIgnoreCase(newCoachNum) ||
                !coach.getTrain().getId().equals(train.getId());

        if (numChangedOrTrainChanged && coachRepository.existsByTrainIdAndCoachNumberIgnoreCase(train.getId(), newCoachNum)) {
            throw new DuplicateResourceException("Coach '" + newCoachNum + "' already exists for Train " + train.getTrainNumber());
        }

        coach.setCoachNumber(newCoachNum);
        coach.setCoachType(requestDto.getCoachType());
        coach.setTotalSeats(requestDto.getTotalSeats());
        coach.setTrain(train);

        Coach updated = coachRepository.save(coach);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteCoach(Long id) {
        if (!coachRepository.existsById(id)) {
            throw new ResourceNotFoundException("Coach not found with id: " + id);
        }
        coachRepository.deleteById(id);
    }

    private CoachResponseDto mapToResponseDto(Coach coach) {
        Train t = coach.getTrain();
        return new CoachResponseDto(
                coach.getId(),
                coach.getCoachNumber(),
                coach.getCoachType(),
                coach.getTotalSeats(),
                t != null ? t.getId() : null,
                t != null ? t.getTrainNumber() : null,
                t != null ? t.getTrainName() : null
        );
    }
}
