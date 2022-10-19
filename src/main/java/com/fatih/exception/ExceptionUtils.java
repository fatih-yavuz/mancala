package com.fatih.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.math.NumberUtils.toInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionUtils {

    public static final String EMPTY_STRING = "";
    public static final String EXCEPTION_CLASS_SEPARATOR = ":";
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\D+");

    public static HttpStatus getHttpStatusFromMessage(final Throwable exception) {
        final String code = NUMBER_PATTERN.matcher(exception.getCause().getMessage()).replaceAll(EMPTY_STRING);
        return HttpStatus.valueOf(toInt(code));
    }

    public static String getExceptionClass(final Throwable exception) {
        return exception.getCause().toString().split(EXCEPTION_CLASS_SEPARATOR)[0];
    }
}
