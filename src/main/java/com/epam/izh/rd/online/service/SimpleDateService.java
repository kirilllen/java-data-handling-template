package com.epam.izh.rd.online.service;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;


public class SimpleDateService implements DateService {

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) throws IllegalArgumentException{
        DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(dateFormat);
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) throws DateTimeParseException {

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(string,dtf2);
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        return formatter.format(localDate);
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        Year year=Year.now();
        long yearLong=year.getLong(ChronoField.YEAR);
        long leapYear=0;
        for(int i=0;i<4;i++){
            yearLong+=1;
            if (Year.isLeap(yearLong)) {
                leapYear=yearLong;
                break;
            }
        }
        return leapYear;
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        return (Year.isLeap(year)) ? 366*24*3600 : 365*24*3600;
    }


}
