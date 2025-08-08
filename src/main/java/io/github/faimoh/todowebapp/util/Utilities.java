package io.github.faimoh.todowebapp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility helper methods for the Spring WebMVC controllers
 */
public class Utilities {
    
    /**
     * Parse date and time strings into a Timestamp
     * @param date Date string in yyyy-MM-dd format
     * @param time Time string in HH:mm or HH:mm:ss format
     * @return Combined Timestamp or null if parsing fails
     */
    public static Timestamp parseDateAndTime(String date, String time) {
        try {
            String dateTimeString = date + " " + time;
            
            // Add seconds if not provided (HH:mm -> HH:mm:ss)
            if (time != null && time.matches("\\d{2}:\\d{2}$")) {
                dateTimeString = date + " " + time + ":00";
            }
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = format.parse(dateTimeString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            System.err.println("Error parsing date/time: " + e.getMessage());
            System.err.println("Input was: date='" + date + "', time='" + time + "'");
            return null;
        }
    }
    
    /**
     * Parse request parameter with default value
     * @param value The parameter value to parse
     * @param defaultValue Default value if parameter is null or empty
     * @return Parsed value or default
     */
    public static String parseRequestParameterWithDefault(String value, String defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value.trim();
    }
}
