package com.ford.javatest.util;

import java.time.LocalDate;

public class DiscountUtils {

    public static LocalDate getEndofNextMonthDate(LocalDate fromDate) {
        LocalDate endDateNextMonth = fromDate.plusMonths(1);
        endDateNextMonth = endDateNextMonth.withDayOfMonth(
                endDateNextMonth.getMonth().length(endDateNextMonth.isLeapYear()));

        return endDateNextMonth;
    }
}
