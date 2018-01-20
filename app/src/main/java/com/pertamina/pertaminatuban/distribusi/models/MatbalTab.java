package com.pertamina.pertaminatuban.distribusi.models;

import java.util.ArrayList;

/**
 * Created by fata on 1/20/2018.
 */

public class MatbalTab {
    private String title;
    private static ArrayList<Matbal> matbals;

    public MatbalTab(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static ArrayList<Matbal> getMatbals() {
        return matbals;
    }

    public static void setMatbals(ArrayList<Matbal> matbals) {
        MatbalTab.matbals = matbals;
    }
}
