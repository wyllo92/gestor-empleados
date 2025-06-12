package com.app.gestor_empleados.config;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateAdapter extends XmlAdapter<String, Date> {
    private final SimpleDateFormat dateFormat;

    public DateAdapter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return dateFormat.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        if (v == null) {
            return null;
        }
        return dateFormat.format(v);
    }
} 