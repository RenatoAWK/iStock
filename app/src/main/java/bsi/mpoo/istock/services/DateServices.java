package bsi.mpoo.istock.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateServices {
    public static String localDateToFormatedToString(LocalDate localDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.Date.FORMAT_DATE);
        return localDate.format(dateTimeFormatter);
    }
    public static String localDateToString(LocalDate localDate){
        StringBuilder stringBuilder = new StringBuilder();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        stringBuilder.append(year);
        if (month < 10){
            stringBuilder.append(0);
        }
        stringBuilder.append(month);
        if (day < 10){
            stringBuilder.append(0);
        }
        stringBuilder.append(day);
        return stringBuilder.toString();
    }

    public static LocalDate stringToLocalDate(String string){
        int year = Integer.parseInt(string.substring(0,4));
        int month = Integer.parseInt(string.substring(4,6));
        int day =  Integer.parseInt(string.substring(6,8));
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate;

    }
}