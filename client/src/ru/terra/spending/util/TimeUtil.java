package ru.terra.spending.util;

import java.util.Date;

public class TimeUtil {
    public static String fromDate(long epoch) {
        String time = "";
        try {
            Date currentDate = new Date();
            long diffInSeconds = (currentDate.getTime() - epoch) / 1000;
            String elapsed = "";
            long seconds = diffInSeconds;
            long mins = diffInSeconds / 60;
            long hours = diffInSeconds / 3600;
            long days = diffInSeconds / 86400;
            long weeks = diffInSeconds / 604800;
            long months = diffInSeconds / 2592000;

            if (seconds < 120) {
                elapsed = "минуту назад";
            } else if (mins < 60) {
                elapsed = mins + " минут";
                if (mins == 1)
                    elapsed += "у";
                else if (mins >= 2 && mins < 5)
                    elapsed += "ы";
                elapsed += " назад";
            } else if (hours < 24) {
                elapsed = hours + " ";
                elapsed += "час";
                if (hours > 20)
                    hours -= 20;
                if (hours >= 2 && hours <= 4)
                    elapsed += "а";
                else if (hours >= 5)
                    elapsed += "ов";
                elapsed += " назад";
            } else if (hours < 48) {
                elapsed = "вчера";
            } else if (days < 7) {
                elapsed = days + " ";
                if (days == 1)
                    elapsed += "день";
                else if (days >= 2 && days <= 4)
                    elapsed += "дня";
                else
                    elapsed += "дней";
                elapsed += " назад";
            } else if (weeks < 5) {
                elapsed = weeks + " ";
                if (weeks == 1)
                    elapsed += "неделю";
                else if (weeks >= 2 && weeks <= 4)
                    elapsed += "недели";
                else
                    elapsed += "недель";
                elapsed += " назад";
            } else if (months < 12) {
                elapsed = months + " ";
                // + (months > 1 ? "месяцев" : "месяц")
                if (months == 1)
                    elapsed += "месяц";
                else if (months >= 2 && months <= 4)
                    elapsed += "месяца";
                else
                    elapsed += "месяцев";
                elapsed += " назад";
            } else {
                elapsed = "больше года назад";
            }
            return elapsed;
        } catch (Exception e) {
            return time;
        }
    }
}
