package project.office.util;


import project.office.model.exception.PropertyInvalidException;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public final class EnumUtil {

    private EnumUtil() {
        // Prevent instantiation
    }

    /**
     * Validates and parses a string into the specified enum type.
     * Case-sensitive by default.
     *
     * @param <T> the enum type
     * @param enumClass the enum class
     * @param value the string value to parse
     * @param fieldName the name of the field (for error message)
     * @return the enum constant
     * @throws PropertyInvalidException if value is null, blank, or not a valid enum constant
     */
    public static <T extends Enum<T>> T parseEnum(
            Class<T> enumClass,
            String value,
            String fieldName) {

        if (value == null || value.isBlank()) {
            throw new PropertyInvalidException(fieldName + " cannot be null or empty");
        }

        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            String validValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new PropertyInvalidException(
                    String.format("%s is invalid. Allowed values: [%s]", fieldName, validValues)
            );
        }
    }

    /**
     * Overloaded version with default field name "Value"
     */
    public static <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value) {
        return parseEnum(enumClass, value, "Value");
    }

    /**
     * Case-insensitive version
     */
    public static <T extends Enum<T>> T parseEnumIgnoreCase(
            Class<T> enumClass,
            String value,
            String fieldName) {

        if (value == null || value.isBlank()) {
            throw new PropertyInvalidException(fieldName + " cannot be null or empty");
        }

        String trimmed = value.trim();
        for (T constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(trimmed)) {
                return constant;
            }
        }

        String validValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        throw new PropertyInvalidException(
                String.format("%s is invalid. Allowed values (case-insensitive): [%s]", fieldName, validValues)
        );
    }

    public static <T extends Enum<T>> T parseEnumIgnoreCase(Class<T> enumClass, String value) {
        return parseEnumIgnoreCase(enumClass, value, "Value");
    }
}