package moco.qiesfrontend.session;

import java.util.HashMap;
import java.util.Map;

import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * AgentSession
 */
public class AgentSession extends ActiveSession implements Session {

    private int changedTickets = 0;
    private int totalCancelledTickets = 0;
    private Map<String, Integer> cancelledTickets;

    public AgentSession() {
        cancelledTickets = new HashMap<String, Integer>();
    }

    @Override
    public void process(SessionManager manager, TransactionQueue queue) {
        Input input = manager.getInput();
        boolean run = true;
        TransactionRecord record = null;
        String goodMessage = "Logged in as Agent. Enter command to begin a transaction.";
        String message = goodMessage;
        String command;

        while (run) {
            command = input.takeInput(message);

            switch (command) {
            case "sellticket":
                record = sellTicket(input, manager);
                message = goodMessage;
                break;
            case "changeticket":
                record = changeTicket(input, manager, changedTickets);
                if (record != null) {
                    changedTickets += record.getNumberTickets().getNumber();
                }
                message = goodMessage;
                break;
            case "cancelticket":
                record = cancelTicket(input, manager, totalCancelledTickets, cancelledTickets);
                if (record != null) {                    
                    if (cancelledTickets.containsKey(record.getSourceNumber().getNumber())) {
                        int temp = cancelledTickets.get(record.getSourceNumber().getNumber());
                        cancelledTickets.replace(record.getSourceNumber().getNumber(), temp, temp + record.getNumberTickets().getNumber());
                    } else {
                        cancelledTickets.put(record.getSourceNumber().getNumber(), record.getNumberTickets().getNumber());
                    }
                    totalCancelledTickets += record.getNumberTickets().getNumber();
                }
                message = goodMessage;
                break;
            case "logout":
                record = logout(input);
                run = false;
                break;
            default:
                message = "Invalid input. Logged in as Agent. Enter command to begin a transaction.";
                break;
            }

            if (record != null)
                queue.push(record);
        }

        manager.setSession(new NoSession()); // This is also a stoping of flow and may need to be fixed
    }

}