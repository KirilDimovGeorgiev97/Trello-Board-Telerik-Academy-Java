package com.telerikacademy.oop.trelloboard.models.common;

import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;

public class Validator {

    public static void checkIfNull(Object obj, String message) {
        if (obj == null)
            throw new InvalidInputException(message);
    }

    private static void checkStringLength(String s, int min, int max, String message) {
        if (s.length() < min || s.length() > max)
            throw new InvalidInputException(message);
    }

    public static void validateString(String s, int min, int max, String message) {
        checkIfNull(s, message);
        checkStringLength(s, min, max, message);
    }

    public static void validateStatus(String className, Status status) {
        if (!(className.replace("Impl", "")
                .equals(status.getClass().getSimpleName().replace("Status", "")))) {
            throw new InvalidInputException("Wrong status is entered");
        }
    }

    public static void validateDecimalArguments(double value, String message) {
        if (value < 0) {
            throw new InvalidInputException(message);
        }
    }
}
