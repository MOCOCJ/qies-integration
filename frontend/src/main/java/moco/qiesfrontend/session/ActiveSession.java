package moco.qiesfrontend.session;

import java.util.Map;

import moco.qiesfrontend.transaction.CancelTicket;
import moco.qiesfrontend.transaction.ChangeTicket;
import moco.qiesfrontend.transaction.CreateService;
import moco.qiesfrontend.transaction.DeleteService;
import moco.qiesfrontend.transaction.Logout;
import moco.qiesfrontend.transaction.SellTicket;
import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * ActiveSession
 */
public abstract class ActiveSession implements Session {

    public TransactionRecord logout(Input input) {
        Logout logout = new Logout();
        return logout.makeTransaction(input);
    }

    public TransactionRecord sellTicket(Input input, SessionManager manager) {
        SellTicket sellTicket = new SellTicket();
        return sellTicket.makeTransaction(input, manager);
    }

    // This cancelTicket funtion is for the Planner
    public TransactionRecord cancelTicket(Input input, SessionManager manager) {
        CancelTicket cancelTicket = new CancelTicket();
        return cancelTicket.makeTransaction(input, manager);
    }

    // This cancelTicket funtion is for the Agent (ticketCount for tracking prev
    // canceled tickets, canceledTickets for tracking past canceled tickets)
    public TransactionRecord cancelTicket(Input input, SessionManager manager, int ticketCount,
            Map<String, Integer> canceledTickets) {
        CancelTicket cancelTicket = new CancelTicket();
        return cancelTicket.makeTransaction(input, manager, ticketCount, canceledTickets);
    }

    // This changeTicket funtion is for the Planner
    public TransactionRecord changeTicket(Input input, SessionManager manager) {
        ChangeTicket changeTicket = new ChangeTicket();
        return changeTicket.makeTransaction(input, manager);
    }

    // This changeTicket funtion is for the Agent (ticketCount for tracking prev
    // changed tickets)
    public TransactionRecord changeTicket(Input input, SessionManager manager, int ticketCount) {
        ChangeTicket changeTicket = new ChangeTicket();
        return changeTicket.makeTransaction(input, manager, ticketCount);
    }

    public TransactionRecord createService(Input input, SessionManager manager) {
        CreateService createService = new CreateService();
        return createService.makeTransaction(input, manager);
    }

    public TransactionRecord deleteService(Input input, SessionManager manager) {
        DeleteService deleteService = new DeleteService();
        return deleteService.makeTransaction(input, manager);
    }

}