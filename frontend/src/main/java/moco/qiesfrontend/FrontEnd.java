package moco.qiesfrontend;

import java.util.logging.Level;

import lombok.extern.java.Log;
import moco.qiesfrontend.session.SessionManager;

@Log
public class FrontEnd {

    public static void main(String[] args) {
        int exitCode = 0;

        if (args.length != 2) {
            log.log(Level.SEVERE, "Incorrect number of arguments " + args.length);
            exitCode = 1;
            System.exit(exitCode);
        }

        SessionManager sessionManager = new SessionManager(args[0], args[1]);
        sessionManager.operate();

        System.exit(exitCode);
    }
}
