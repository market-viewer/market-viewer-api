package jotalac.market_viewer.market_viewer_app.util;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class TimezoneProvider {
    private static final Map<String, String> TIMEZONES;

    static {
        try (InputStream is = TimezoneProvider.class.getResourceAsStream("/data/timezones.json")) {
           ObjectMapper mapper = new ObjectMapper();
           TIMEZONES = mapper.readValue(is, new TypeReference<>() {});
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load timezones: " + e.getMessage());
        }
    }

    private TimezoneProvider() {}

    public static Map<String, String> getTimezones() {
        return TIMEZONES;
    }

    public static String getTimezoneCode(String timezone) {
        return TIMEZONES.get(timezone);
    }
}
