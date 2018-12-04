package moco.qiesfrontend.transaction;

import java.util.Map;

import moco.qiesfrontend.session.Input;
import moco.qiesfrontend.session.SessionManager;
import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * Transaction
 */
public abstract class Transaction {

    protected TransactionRecord record;

    /**
     * This method is spesificaly for Logout as to not have to pass manager
     * 
     * @param input Used for the promt
     * @return record object
     */
    public abstract TransactionRecord makeTransaction(Input input);

    /**
     * This is for all transaction that are Planner only and do not require a ticket
     * counter
     * 
     * @param input   Used for the promt
     * @param manager Used so that we can use session functions
     * @return record object
     */
    public abstract TransactionRecord makeTransaction(Input input, SessionManager manager);

    /**
     * This is for Agent transactions that requier the ticket count to make checks
     * 
     * @param input       Used for the promt
     * @param manager     Used so that we can use session functions
     * @param ticketCount Used for ticket counting checks
     * @return record object
     */
    public abstract TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount);

    /**
     * This is for Agent transaction that requier previous canceled tickets as well
     * as ticket count to make proper checks
     * 
     * @param input           Used for the promt
     * @param manager         Used so that we can use session functions
     * @param ticketCount     Used for ticket counting checks
     * @param canceledTickets Used for checking previous canceled tickets
     * @return record object
     */
    public abstract TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount,
            Map<String, Integer> canceledTickets);

}