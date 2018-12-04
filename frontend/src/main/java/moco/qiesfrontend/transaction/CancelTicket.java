package moco.qiesfrontend.transaction;

import java.util.Map;

import moco.qiesfrontend.session.Input;
import moco.qiesfrontend.session.SessionManager;
import moco.qiesfrontend.transaction.record.NumberTickets;
import moco.qiesfrontend.transaction.record.ServiceNumber;
import moco.qiesfrontend.transaction.record.TransactionCode;
import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * CancelTicket
 */
public class CancelTicket extends Transaction {
    // Vars
    public static TransactionCode CODE = TransactionCode.CAN;

    public CancelTicket() {
        record = new TransactionRecord(CODE);
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount,
            Map<String, Integer> canceledTickets) {
        String serviceNumberIn;
        String numTicketsIn;
        ServiceNumber serviceNumber;
        NumberTickets numberTickets;

        serviceNumberIn = input.takeInput("Enter service number of ticket you would like to cancel.");
        try {
            serviceNumber = new ServiceNumber(serviceNumberIn, manager);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        numTicketsIn = input.takeInput("Enter number of tickets you want to cancel."); // remove paseInt
        try {
            numberTickets = new NumberTickets(numTicketsIn);
            if (numberTickets.getNumber() > 10) {
                System.out.print("Cannot cancel more then 10 tickets at once.");
                throw new IllegalArgumentException();
            } else if (canceledTickets.containsKey(serviceNumber.getNumber())
                    && numberTickets.getNumber() + canceledTickets.get(serviceNumber.getNumber()) > 10) {
                System.out.format(
                        "Cannot cancel more then 10 tickets for a single service.\nUser has %d tickets left to cancel for this service.",
                        10 - canceledTickets.get(serviceNumber.getNumber()));
                throw new IllegalArgumentException();
            } else if (numberTickets.getNumber() + ticketCount > 20) {
                System.out.format(
                        "Cannot cancel as total session canceled tickets would be over 20.\nUser has %d tickets left to cancel this session.",
                        20 - ticketCount);
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.print("\nInvalid number of tickets. ");
            return null;
        }
        System.out.format("%s ticket(s) canceled from service %s\n", numTicketsIn, serviceNumber);
        record.setSourceNumber(serviceNumber);
        record.setNumberTickets(numberTickets);

        return record;
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager) {
        String serviceNumberIn;
        String numTicketsIn;
        ServiceNumber serviceNumber;
        NumberTickets numberTickets;

        serviceNumberIn = input.takeInput("Enter service number of ticket you would like to cancel.");
        try {
            serviceNumber = new ServiceNumber(serviceNumberIn, manager);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        numTicketsIn = input.takeInput("Enter number of tickets you want to cancel."); // remove paseInt
        try {
            numberTickets = new NumberTickets(numTicketsIn);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid number of tickets.");
            return null;
        }

        System.out.format("%s ticket(s) canceled from service %s.\n", numTicketsIn, serviceNumber);
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

}