package org.l0gik67.entity;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

public record Birthday(LocalDate birthDate) {
    public long getAge(){
        return YEARS.between(birthDate, LocalDate.now());
    }
}
