package fii.workflow.manager.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateString {
    String year;
    String month;
    String day;
    private DateString() {}

    public static DateString fromLocalDate(LocalDate localDate) {
        DateString dateString = new DateString();
        dateString.year = String.valueOf(localDate.getYear());
        dateString.month = String.format("%02d", localDate.getMonthValue());
        dateString.day = String.format("%02d", localDate.getDayOfMonth());
        return dateString;
    }
}
