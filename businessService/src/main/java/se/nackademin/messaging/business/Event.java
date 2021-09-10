package se.nackademin.messaging.business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public abstract class Event {

    enum AuditEventType {
        DEPOSIT,
        WITHDRAW,
        OPEN_ACCOUNT
    }

    @JsonProperty("type")
    private final String type;
    @JsonProperty("accountId")
    private final long accountId;
    @JsonProperty("timestamp")
    private final Instant timestamp;
    @JsonProperty("data")
    private final String data;

    protected Event(AuditEventType type, long accountId, Instant timestamp, String data) {
        this.type = type.toString();
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.data = data;
    }
}
