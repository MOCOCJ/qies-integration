package moco.qiesbackend;

import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class BackEnd {

    public static void main(String[] args) {
        int exitCode = 0;

        if (args.length != 4) {
            log.log(Level.SEVERE, "Incorrect number of arguments " + args.length);
            exitCode = 1;
            System.exit(exitCode);
        }

        // inputs are
        // the file name of the Merged Transaction Summary File,
        // the file name of the Old Central Services File,
        // the file name of the New Central Services File, and
        // the file name of the New Valid Services File.

        BackEndManager manager = new BackEndManager(args[0], args[1], args[2], args[3]);
        manager.operate();

        System.exit(exitCode);
    }
}
