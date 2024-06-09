package com.kolosov.openmeteosdk;

import java.math.BigDecimal;

public record Location(BigDecimal latitude, BigDecimal longitude) {

    public Location {
        validateLatitude(latitude);
        validateLongitude(longitude);
    }

    public static Location pickleball() {
        return new Location(new BigDecimal("7.54156"), new BigDecimal("98.21083"));
    }

    private static void validateLatitude(BigDecimal latitude) {
        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0 || latitude.compareTo(BigDecimal.valueOf(90)) > 0) {
            throw new IllegalArgumentException("Invalid latitude: " + latitude);
        }
    }

    private static void validateLongitude(BigDecimal longitude) {
        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0 || longitude.compareTo(BigDecimal.valueOf(180)) > 0) {
            throw new IllegalArgumentException("Invalid longitude: " + longitude);
        }
    }
}