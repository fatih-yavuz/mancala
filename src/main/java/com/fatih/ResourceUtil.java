package com.fatih;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceUtil {

    public static JsonObject readAndParse(final String filePath) throws IOException {
        return (JsonObject) new JsonParser().parse(read(filePath));
    }

    public static String read(final String filePath) throws IOException {
        return IOUtils.toString(
                Objects.requireNonNull(Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(filePath)),
                StandardCharsets.UTF_8
        );
    }


}
