package com.railway.reservation.repository;

import com.railway.reservation.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByTrainNumber(String trainNumber);
    boolean existsByTrainNumber(String trainNumber);

    List<Train> findBySourceStationIdAndDestinationStationId(Long sourceStationId, Long destinationStationId);

    @Query("SELECT t FROM Train t WHERE LOWER(t.trainName) LIKE LOWER(CONCAT('%', :query, '%')) OR t.trainNumber LIKE CONCAT('%', :query, '%')")
    List<Train> searchByTrainNameOrNumber(@Param("query") String query);
}
