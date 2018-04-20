package com.pertamina.pertaminatuban.utils;

import java.text.DecimalFormat;

public class MethodCollection {
    public static String numberWithDot(long angka) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(angka).replaceAll(",",".");
    }

    public static String numberWithDot(double angka) {
        long number = (long)angka;
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number).replaceAll(",",".");
    }

    public static String numberWithDot(String angka) {
        long number = Long.parseLong(angka);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number).replaceAll(",",".");
    }
}
