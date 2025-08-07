/*
 * Copyright (c) 2020, Faisal Ahmed Pasha Mohammed
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.faimoh.todowebapp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility helper methods for the Spring WebMVC controllers
 * 
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
public class Utilities {
    
    /**
     * Parse date and time strings into a Timestamp
     * @param date Date string in yyyy-MM-dd format
     * @param time Time string in HH:mm:ss format
     * @return Combined Timestamp or null if parsing fails
     */
    public static Timestamp parseDateAndTime(String date, String time) {
        try {
            String dateTimeString = date + " " + time;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = format.parse(dateTimeString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            System.err.println("Error parsing date/time: " + e.getMessage());
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
