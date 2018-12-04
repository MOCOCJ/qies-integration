package moco.qiesfrontend.session;

/**
 * NoSession
 */
public class NoSession implements Session {

    @Override
    public void process(SessionManager manager, TransactionQueue queue) {
        Input input = manager.getInput();
        boolean run = true;
        String message = "You are not logged in, please log in before performing any other actions. (login)";
        String userInput;

        while (run) {
            userInput = input.takeInput(message);

            if (userInput.equals("login")) {
                run = !logIn(manager);
            }
            message = "Invalid input. You are not logged in, please log in before performing any other actions. (login)";
        }
    }

    // There is a problem here. We never return the flow back to NoSession ager the
    // .setSession command. Check this
    public boolean logIn(SessionManager manager) {
        Input input = manager.getInput();
        String message = "Login as agent or planner.";
        boolean success = false;
        String userType = input.takeInput(message);

        switch (userType) {
        case "agent":
            manager.setSession(new AgentSession());
            success = true;
            break;
        case "planner":
            manager.setSession(new PlannerSession());
            success = true;
            break;
        default:
            break;
        }

        return success;
    }
}