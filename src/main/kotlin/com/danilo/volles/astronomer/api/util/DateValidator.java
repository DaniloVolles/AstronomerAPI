package com.danilo.volles.astronomer.api.util;

import com.danilo.volles.astronomer.api.exception.InvalidDateStringException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateValidator {

    public static LocalDate getLocalDateFromString(LocalDate date) {
        return date;
//        try {
//            return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
//        } catch (DateTimeParseException e) {
//            throw new InvalidDateStringException();
//        }
    }


}
