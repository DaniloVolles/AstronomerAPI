package com.danilo.volles.astronomer.api.util;

import com.danilo.volles.astronomer.api.exception.InvalidDateStringExeption;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateValidator {

    public static LocalDate getLocalDateFromString(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new InvalidDateStringExeption();
        }
    }


}
