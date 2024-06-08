package com.kolosov.openmeteosdk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Location {
    PICKLEBALL(7.54156, 98.21083);

    private final double latitude;
    private final double longitude;
}
