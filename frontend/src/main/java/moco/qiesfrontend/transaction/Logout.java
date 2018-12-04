package moco.qiesfrontend.transaction;

import java.util.Map;

import moco.qiesfrontend.session.Input;
import moco.qiesfrontend.session.SessionManager;
import moco.qiesfrontend.transaction.record.TransactionCode;
import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * Logout
 */
public class Logout extends Transaction {
    // Vars
    public static TransactionCode CODE = TransactionCode.EOS;

    public Logout() {
        record = new TransactionRecord(CODE);
    }

    @Override
    public TransactionRecord makeTransaction(Input input) {
        return record;
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager) {
        return null;
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount) {
        return null;
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount,
            Map<String, Integer> canceledTickets) {
        return null;
    }

}