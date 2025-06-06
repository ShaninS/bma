package com.example.dashboarddesign;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public record CadetAudit(
        Date timestamp,
        String action,
        String cadetId,
        String cadetName,
        String cadetEmail,
        String cadetUsername,
        String cadetBatch
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CadetAudit{" +
                "timestamp=" + timestamp +
                ", action='" + action + '\'' +
                ", cadetId='" + cadetId + '\'' +
                ", cadetName='" + cadetName + '\'' +
                ", cadetEmail='" + cadetEmail + '\'' +
                ", cadetUsername='" + cadetUsername + '\'' +
                ", cadetBatch='" + cadetBatch + '\'' +
                '}';
    }
}