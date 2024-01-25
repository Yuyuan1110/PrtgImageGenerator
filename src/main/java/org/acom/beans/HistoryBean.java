package org.acom.beans;

import org.acom.Exception.InvalidDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryBean {
    String id;
    String startDate;
    String endDate;

    private static final String DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    public HistoryBean() {
    }

    public HistoryBean(String id, String startDate, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() throws InvalidDateException {
        if (startDate != null && !startDate.isEmpty()) {
            try {
                sdf.setLenient(false);
                Date inputDate = sdf.parse(startDate);
                Date currentDate = new Date();
                Date end = sdf.parse(getEndDate());

                if (inputDate.before(currentDate) && inputDate.before(end)) {
                    return startDate;
                } else {
                    throw new InvalidDateException("Error: Input date should not be greater than current system time or end time.");
                }
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please use \"" + DATE_FORMAT + "\".");
                throw new RuntimeException(e);
            }
        } else {
            return new SimpleDateFormat(DATE_FORMAT).format(new Date().getTime() - 60 * 60 * 24 * 30 * 1000L);
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() throws InvalidDateException {
        if (endDate != null && !endDate.isEmpty()) {
            try {
                Date inputDate = sdf.parse(endDate);
                Date currentDate = new Date();
                Date start = sdf.parse(startDate.isEmpty() ? new SimpleDateFormat(DATE_FORMAT).format(new Date()) : startDate);

                if (inputDate.before(currentDate) && inputDate.after(start)) {
                    return endDate;
                } else {
                    throw new InvalidDateException("Error: The input date should not be greater than the current system time or less than the starting time.");
                }
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please use \"" + DATE_FORMAT + "\".");
                throw new RuntimeException(e);
            }
        } else {
            return new SimpleDateFormat(DATE_FORMAT).format(new Date());
        }
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
