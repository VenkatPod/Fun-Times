package com.ford.javatest.util;

import com.ford.javatest.repos.InMemoryDiscountRepo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DiscountUtilsTest {

    @Test
    public void getAppleDiscountEndDate_givenFromDateHas31Days_shouldReturnEndOfNextMonthDateAs30() {
        LocalDate expectedEndDate = LocalDate.of(2020, 11, 30);
        LocalDate currentDate = LocalDate.of(2020, 10, 18);
        assertEquals(expectedEndDate, DiscountUtils.getEndofNextMonthDate(currentDate));
    }

    @Test
    public void getAppleDiscountEndDate_givenFromDateHas30Days_shouldReturnEndOfNextMonthDateAs31() {
        LocalDate expectedEndDate = LocalDate.of(2020, 10, 31);
        LocalDate currentDate = LocalDate.of(2020, 9, 18);
        assertEquals(expectedEndDate, DiscountUtils.getEndofNextMonthDate(currentDate));
    }

    @Test
    public void getAppleDiscountEndDate_givenFromDateHasThisYear_shouldReturnEndOfNextMonthDateFromNextYear() {
        LocalDate expectedEndDate = LocalDate.of(2021, 1, 31);
        LocalDate currentDate = LocalDate.of(2020, 12, 18);
        assertEquals(expectedEndDate, DiscountUtils.getEndofNextMonthDate(currentDate));
    }

    @Test
    public void getAppleDiscountEndDate_givenFromDateOfLeapYear_shouldReturnEndOfNextMonthDateAsEndOfTheFebMonth() {
        LocalDate expectedEndDate = LocalDate.of(2020, 2, 29);
        LocalDate currentDate = LocalDate.of(2020, 1, 18);
        assertEquals(expectedEndDate, DiscountUtils.getEndofNextMonthDate(currentDate));
    }

    @Test
    public void getAppleDiscountEndDate_givenFromDateOfNonLeapYear_shouldReturnEndOfNextMonthDateAsEndOfTheFebMonth() {
        LocalDate expectedEndDate = LocalDate.of(2021, 2, 28);
        LocalDate currentDate = LocalDate.of(2021, 1, 18);
        assertEquals(expectedEndDate, DiscountUtils.getEndofNextMonthDate(currentDate));
    }

}