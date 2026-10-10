package com.railway.reservation.entity.enums;

public enum CoachType {
    AC_FIRST_CLASS(3.0),
    AC_2_TIER(2.2),
    AC_3_TIER(1.5),
    SLEEPER(1.0),
    SECOND_SITTING(0.6),
    CHAIR_CAR(1.2);

    private final double fareMultiplier;

    CoachType(double fareMultiplier) {
        this.fareMultiplier = fareMultiplier;
    }

    public double getFareMultiplier() {
        return fareMultiplier;
    }
}
