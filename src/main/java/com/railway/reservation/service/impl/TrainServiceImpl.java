package com.railway.reservation.service.impl;

import com.railway.reservation.dto.request.TrainRequestDto;
import com.railway.reservation.dto.response.CoachResponseDto;
import com.railway.reservation.dto.response.StationResponseDto;
import com.railway.reservation.dto.response.TrainResponseDto;
import com.railway.reservation.entity.Coach;
import com.railway.reservation.entity.Station;
import com.railway.reservation.entity.Train;
import com.railway.reservation.exception.DuplicateResourceException;
import com.railway.reservation.exception.ResourceNotFoundException;
import com.railway.reservation.repository.CoachRepository;
import com.railway.reservation.repository.StationRepository;
import com.railway.reservation.repository.TrainRepository;
import com.railway.reservation.service.TrainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final CoachRepository coachRepository;

    public TrainServiceImpl(TrainRepository trainRepository,
                            StationRepository stationRepository,
                            CoachRepository coachRepository) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.coachRepository = coachRepository;
    }

    @Override
    public TrainResponseDto createTrain(TrainRequestDto requestDto) {
        String trainNumber = requestDto.getTrainNumber().trim();
        if (trainRepository.existsByTrainNumber(trainNumber)) {
            throw new DuplicateResourceException("Train with number '" + trainNumber + "' already exists");
        }

        Station sourceStation = stationRepository.findById(requestDto.getSourceStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Source station not found with id: " + requestDto.getSourceStationId()));

        Station destinationStation = stationRepository.findById(requestDto.getDestinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination station not found with id: " + requestDto.getDestinationStationId()));

        if (sourceStation.getId().equals(destinationStation.getId())) {
            throw new IllegalArgumentException("Source station and destination station cannot be the same");
        }

        Train train = new Train(
                trainNumber,
                requestDto.getTrainName().trim(),
                requestDto.getTrainType(),
                sourceStation,
                destinationStation,
                requestDto.getDepartureTime(),
                requestDto.getArrivalTime(),
                requestDto.getTotalDistanceKm(),
                requestDto.getBaseFare()
        );

        Train saved = trainRepository.save(train);
        return mapToResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponseDto> getAllTrains() {
        return trainRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TrainResponseDto getTrainById(Long id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + id));
        return mapToResponseDto(train);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainResponseDto getTrainByNumber(String trainNumber) {
        Train train = trainRepository.findByTrainNumber(trainNumber.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));
        return mapToResponseDto(train);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponseDto> searchTrains(Long sourceStationId, Long destinationStationId) {
        return trainRepository.findBySourceStationIdAndDestinationStationId(sourceStationId, destinationStationId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponseDto> searchByNameOrNumber(String query) {
        return trainRepository.searchByTrainNameOrNumber(query.trim())
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainResponseDto updateTrain(Long id, TrainRequestDto requestDto) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + id));

        String newTrainNum = requestDto.getTrainNumber().trim();
        if (!train.getTrainNumber().equalsIgnoreCase(newTrainNum) &&
                trainRepository.existsByTrainNumber(newTrainNum)) {
            throw new DuplicateResourceException("Train with number '" + newTrainNum + "' already exists");
        }

        Station sourceStation = stationRepository.findById(requestDto.getSourceStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Source station not found with id: " + requestDto.getSourceStationId()));

        Station destinationStation = stationRepository.findById(requestDto.getDestinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination station not found with id: " + requestDto.getDestinationStationId()));

        if (sourceStation.getId().equals(destinationStation.getId())) {
            throw new IllegalArgumentException("Source station and destination station cannot be the same");
        }

        train.setTrainNumber(newTrainNum);
        train.setTrainName(requestDto.getTrainName().trim());
        train.setTrainType(requestDto.getTrainType());
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        train.setDepartureTime(requestDto.getDepartureTime());
        train.setArrivalTime(requestDto.getArrivalTime());
        train.setTotalDistanceKm(requestDto.getTotalDistanceKm());
        train.setBaseFare(requestDto.getBaseFare());

        Train updated = trainRepository.save(train);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteTrain(Long id) {
        if (!trainRepository.existsById(id)) {
            throw new ResourceNotFoundException("Train not found with id: " + id);
        }
        trainRepository.deleteById(id);
    }

    private TrainResponseDto mapToResponseDto(Train train) {
        List<Coach> coachList = coachRepository.findByTrainId(train.getId());
        List<CoachResponseDto> coachDtos = coachList.stream()
                .map(c -> new CoachResponseDto(
                        c.getId(),
                        c.getCoachNumber(),
                        c.getCoachType(),
                        c.getTotalSeats(),
                        train.getId(),
                        train.getTrainNumber(),
                        train.getTrainName()
                ))
                .collect(Collectors.toList());

        Station src = train.getSourceStation();
        Station dest = train.getDestinationStation();

        StationResponseDto srcDto = src != null ? new StationResponseDto(
                src.getId(), src.getStationCode(), src.getStationName(), src.getCity(), src.getState()
        ) : null;

        StationResponseDto destDto = dest != null ? new StationResponseDto(
                dest.getId(), dest.getStationCode(), dest.getStationName(), dest.getCity(), dest.getState()
        ) : null;

        return new TrainResponseDto(
                train.getId(),
                train.getTrainNumber(),
                train.getTrainName(),
                train.getTrainType(),
                srcDto,
                destDto,
                train.getDepartureTime(),
                train.getArrivalTime(),
                train.getTotalDistanceKm(),
                train.getBaseFare(),
                coachDtos
        );
    }
}
