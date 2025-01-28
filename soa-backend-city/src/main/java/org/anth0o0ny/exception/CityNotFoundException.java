package org.anth0o0ny.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class CityNotFoundException extends RuntimeException {
    private final int id;
}
