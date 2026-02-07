package project.office.util;


import project.office.model.exception.PropertyInvalidException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class FormatUtil {

    // Common date patterns (ISO 8601 and others)
    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE,           // 2025-11-16
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd")
    );

    // Common time patterns
    private static final List<DateTimeFormatter> TIME_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_TIME,           // 23:39:45
            DateTimeFormatter.ofPattern("HH:mm:ss"),
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS"),
            DateTimeFormatter.ofPattern("h:mm a"),      // 11:39 PM
            DateTimeFormatter.ofPattern("h:mm:ss a")
    );

    // Common datetime patterns
    private static final List<DateTimeFormatter> DATETIME_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,      // 2025-11-16T23:39:45
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,     // 2025-11-16T23:39:45-08:00
            DateTimeFormatter.ISO_ZONED_DATE_TIME,      // 2025-11-16T23:39:45-08:00[America/Los_Angeles]
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    );

    /**
     * Parses a JSON string into LocalDate using multiple common formats.
     */
    public static LocalDate toLocalDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        String trimmed = dateStr.trim();
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(trimmed, formatter);
            } catch (DateTimeParseException e) {
                throw new PropertyInvalidException(e.getMessage());
            }
        }
        throw new IllegalArgumentException("Unable to parse date: " + dateStr);
    }

    /**
     * Parses a JSON string into LocalTime using multiple common formats.
     */
    public static LocalTime toLocalTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }

        String trimmed = timeStr.trim().toUpperCase(); // For AM/PM handling
        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                return LocalTime.parse(trimmed, formatter);
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }
        throw new IllegalArgumentException("Unable to parse time: " + timeStr);
    }

    /**
     * Parses a JSON string into LocalDateTime using multiple common formats.
     * Strips zone info if present (converts to local).
     */
    public static LocalDateTime toLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }

        String trimmed = dateTimeStr.trim();
        for (DateTimeFormatter formatter : DATETIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(trimmed, formatter);
            } catch (DateTimeParseException e) {
                // Continue
            }
        }

        // Fallback: try parsing with zone and convert to local
        try {
            return java.time.OffsetDateTime.parse(trimmed).toLocalDateTime();
        } catch (DateTimeParseException e1) {
            try {
                return java.time.ZonedDateTime.parse(trimmed).toLocalDateTime();
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Unable to parse datetime: " + dateTimeStr);
            }
        }
    }

    // Convenience methods for JSON processing (e.g., with Jackson or Gson)
    public static LocalDate parseDate(String jsonValue) {
        return toLocalDate(jsonValue);
    }

    public static LocalTime parseTime(String jsonValue) {
        return toLocalTime(jsonValue);
    }

    public static LocalDateTime parseDateTime(String jsonValue) {
        return toLocalDateTime(jsonValue);
    }
}
