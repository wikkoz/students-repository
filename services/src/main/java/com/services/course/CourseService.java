package com.services.course;

import java.time.LocalDate;
import java.util.Calendar;

public class CourseService {

    public String getCurrentSemeter() {
        LocalDate date = LocalDate.now();
        if (date.getMonthValue() >= Calendar.OCTOBER) {
            return date.getYear() + "Z";
        } else {
            return date.getYear() + "L";
        }
    }

    public String getNamespace(String semester, String course) {
        return semester + "_" + course;
    }
}
