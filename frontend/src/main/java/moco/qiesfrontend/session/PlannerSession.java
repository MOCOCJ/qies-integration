package moco.qiesfrontend.session;

import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * PlannerSession
 */
public class PlannerSession extends ActiveSession implements Session {

    @Override
    public void process(SessionManager manager, TransactionQueue queue) {
        Input input = manager.getInput();
        boolean run = true;
        TransactionRecord record = null;
        String goodMessage = "Logged in as Planner. Enter command to begin a transaction.";
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
                record = changeTicket(input, manager);
                message = goodMessage;
                break;
            case "cancelticket":
                record = cancelTicket(input, manager);
                message = goodMessage;
                break;
            case "createservice":
                record = createService(input, manager);
                message = goodMessage;
                break;
            case "deleteservice":
                record = deleteService(input, manager);
                message = goodMessage;
                break;
            case "logout":
                record = logout(input);
                run = false;
                break;
            default:
                message = "Invalid input. Logged in as Planner. Enter command to begin a transaction.";
                break;
            }

            if (record != null)
                queue.push(record);
        }

        manager.setSession(new NoSession());
    }

}