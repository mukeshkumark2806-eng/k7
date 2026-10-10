package com.railway.reservation.repository;

import com.railway.reservation.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findByStationCodeIgnoreCase(String stationCode);
    boolean existsByStationCodeIgnoreCase(String stationCode);
    List<Station> findByCityIgnoreCase(String city);
}
