package com.example.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TimeCalculator {

    public static void timeCal(String[] args) {
        List<LocalDateTime> logs = List.of(
                LocalDateTime.of(2025, 9, 13, 9, 5),
                LocalDateTime.of(2025, 9, 13, 12, 0),
                LocalDateTime.of(2025, 9, 13, 13, 0),
                LocalDateTime.of(2025, 9, 13, 18, 0)
        );

        if (logs.size() % 2 != 0) {
            throw new IllegalArgumentException("Logs size must be even (check-in/check-out pairs).");
        }

        Duration worked = Duration.ZERO;
        for (int i = 0; i < logs.size(); i += 2) {
            LocalDateTime checkIn = logs.get(i);
            LocalDateTime checkOut = logs.get(i + 1);
            worked = worked.plus(Duration.between(checkIn, checkOut));
        }

        // Full day duration (from first check-in to last check-out)
        Duration fullDay = Duration.between(logs.get(0), logs.get(logs.size() - 1));

        // Free time
        Duration freeTime = fullDay.minus(worked);

        System.out.println("Total worked: " + formatDuration(worked));
        System.out.println("Free time: " + formatDuration(freeTime));
    }

    private static String formatDuration(Duration d) {
        long hours = d.toHours();
        long minutes = d.toMinutesPart();
        return hours + "h " + minutes + "m";
    }
}
