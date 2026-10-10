package com.railway.reservation.repository;

import com.railway.reservation.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    List<Coach> findByTrainId(Long trainId);
    Optional<Coach> findByTrainIdAndCoachNumberIgnoreCase(Long trainId, String coachNumber);
    boolean existsByTrainIdAndCoachNumberIgnoreCase(Long trainId, String coachNumber);
}
