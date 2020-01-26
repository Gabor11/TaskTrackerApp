package hu.uni.eszterhazy.exceptions;

import java.time.LocalDate;

public class DateNotOK extends Exception {
    public DateNotOK(LocalDate date) { super(date.toString());}
}
