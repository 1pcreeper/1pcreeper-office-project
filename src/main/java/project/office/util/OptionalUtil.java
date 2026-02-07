package project.office.util;


import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Objects;

public class OptionalUtil {
    public static String getNullIfBlank(@Nullable String str) {
        if (Objects.isNull(str)) {
            return null;
        }
        if (str.trim().isBlank()) {
            return null;
        }
        return str;
    }
    public static LocalDate getOrDefault(@Nullable LocalDate localDate){
        if (Objects.isNull(localDate)){
            return LocalDate.now();
        }
        return localDate;
    }
}
