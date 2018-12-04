package moco.qiesfrontend.transaction;

import java.util.Map;

import moco.qiesfrontend.session.Input;
import moco.qiesfrontend.session.SessionManager;
import moco.qiesfrontend.transaction.record.NumberTickets;
import moco.qiesfrontend.transaction.record.ServiceNumber;
import moco.qiesfrontend.transaction.record.TransactionCode;
import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * ChangeTicket
 */
public class ChangeTicket extends Transaction {
    // Vars
    public static TransactionCode CODE = TransactionCode.CHG;

    public ChangeTicket() {
        record = new TransactionRecord(CODE);
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount) {
        String sourceNumberIn;
        String destinationNumberIn;
        String numTicketsIn;
        ServiceNumber sourceNumber;
        ServiceNumber destinationNumber;
        NumberTickets numberTickets;

        sourceNumberIn = input.takeInput("Enter service number of the service you want to change.");
        try {
            sourceNumber = new ServiceNumber(sourceNumberIn, manager);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        destinationNumberIn = input.takeInput("Enter service number of the service you want to change to.");
        try {
            destinationNumber = new ServiceNumber(destinationNumberIn, manager);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        numTicketsIn = input.takeInput("Enter number of tickets to change.");
        try {
            numberTickets = new NumberTickets(numTicketsIn);
            if (ticketCount + numberTickets.getNumber() > 20) {
                System.out.format(
                        "Cannot change as total session changed tickets would be over 20.\nUser has %d tickets left to change this session.",
                        20 - ticketCount);
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.print("\nInvalid number of tickets.");
            return null;
        }
        System.out.format("%s ticket(s) changed from service %s to service %s\n", numTicketsIn, sourceNumberIn,
                destinationNumberIn);
        record.setSourceNumber(sourceNumber);
        record.setDestinationNumber(destinationNumber);
        record.setNumberTickets(numberTickets);

        return record;
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager) {
        String sourceNumberIn;
        String destinationNumberIn;
        String numTicketsIn;
        ServiceNumber sourceNumber;
        ServiceNumber destinationNumber;
        NumberTickets numberTickets;

        sourceNumberIn = input.takeInput("Enter service number of the service you want to change.");
        try {
            sourceNumber = new ServiceNumber(sourceNumberIn, manager);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        destinationNumberIn = input.takeInput("Enter service number of the service you want to change to.");
        try {
            destinationNumber = new ServiceNumber(destinationNumberIn, manager);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        numTicketsIn = input.takeInput("Enter number of tickets to change.");
        try {
            numberTickets = new NumberTickets(numTicketsIn);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid number of tickets.");
            return null;
        }

        System.out.format("%s ticket(s) changed from service %s to service %s\n", numTicketsIn, sourceNumberIn,
                destinationNumberIn);
        record.setSourceNumber(sourceNumber);
        record.setDestinationNumber(destinationNumber);
        record.setNumberTickets(numberTickets);

        return record;
    }

    @Override
    public TransactionRecord makeTransaction(Input input) {
        return null;
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount,
            Map<String, Integer> canceledTickets) {
        return null;
    }

}