package moco.qiesfrontend.transaction;

import java.util.Map;

import moco.qiesfrontend.session.Input;
import moco.qiesfrontend.session.SessionManager;
import moco.qiesfrontend.transaction.record.NumberTickets;
import moco.qiesfrontend.transaction.record.ServiceNumber;
import moco.qiesfrontend.transaction.record.TransactionCode;
import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * SellTicket
 */
public class SellTicket extends Transaction {
    // Vars
    public static TransactionCode CODE = TransactionCode.SEL;

    public SellTicket() {
        record = new TransactionRecord(CODE);
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager) {
        String serviceNumberIn;
        String numTicketsIn;
        ServiceNumber serviceNumber;
        NumberTickets numberTickets;

        serviceNumberIn = input.takeInput("Enter service number to sell tickets for.");
        try {
            serviceNumber = new ServiceNumber(serviceNumberIn, manager);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        numTicketsIn = input.takeInput("Enter number of tickets to sell.");
        try {
            numberTickets = new NumberTickets(numTicketsIn);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid number of tickets.");
            return null;
        }

        System.out.format("%s ticket(s) sold for service %s\n", numTicketsIn, serviceNumberIn);
        record.setSourceNumber(serviceNumber);
        record.setNumberTickets(numberTickets);

        return record;
    }

    @Override
    public TransactionRecord makeTransaction(Input input) {
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