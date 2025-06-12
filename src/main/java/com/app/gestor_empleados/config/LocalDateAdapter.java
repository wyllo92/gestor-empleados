package com.app.gestor_empleados.config;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;

    @Override
    public LocalDate unmarshal(String date) {
        return date != null ? LocalDate.parse(date, dateFormat) : null;
    }

    @Override
    public String marshal(LocalDate date) {
        return date != null ? date.format(dateFormat) : null;
    }
} 