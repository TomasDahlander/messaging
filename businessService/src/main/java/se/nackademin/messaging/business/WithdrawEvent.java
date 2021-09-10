package se.nackademin.messaging.business;

import java.time.Instant;

public class WithdrawEvent extends Event {
    private WithdrawEvent(long accountId, String data) {
        super(AuditEventType.WITHDRAW, accountId, Instant.now(), data);
    }

    public static WithdrawEvent build(long accountId, long amount) {
        return new WithdrawEvent(accountId, "Withdraw amount of " + amount);
    }
}
