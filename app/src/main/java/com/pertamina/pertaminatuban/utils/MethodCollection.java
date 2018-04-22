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

    public static String numberWithComma(long angka) {
        DecimalFormat precision = new DecimalFormat("0.00");
        return precision.format(angka).replace(".", ",");
    }

    public static String numberWithComma(double angka) {
        DecimalFormat precision = new DecimalFormat("0.00");
        return precision.format(angka).replace(".", ",");
    }

    public static String numberWithComma(String angka) {
        double number = Double.parseDouble(angka);
        DecimalFormat precision = new DecimalFormat("0.00");
        return precision.format(number).replace(".", ",");
    }
}
