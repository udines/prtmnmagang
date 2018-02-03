package com.pertamina.pertaminatuban.marine.models;

import java.sql.Timestamp;

public class TemporaryStop {
    private String id;
    private String foreignKey;

    private String reason;
    private Timestamp stopTime;
    private Timestamp resumeTime;
}
