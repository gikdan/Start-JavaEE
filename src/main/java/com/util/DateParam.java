package com.util;

import com.generic.exception.InvalidDateException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParam {
    private final Date date;

    public DateParam(String dateStr) throws InvalidDateException {
        if (dateStr != null && !dateStr.isEmpty()) {

            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                throw new InvalidDateException();
            }
        }
        else
            this.date=null;
    }

    public Date getDate() {
        return date;
    }
}