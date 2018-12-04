package moco.qiesbackend;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import lombok.extern.java.Log;
import moco.qiesbackend.parser.CentralServicesParser;
import moco.qiesbackend.parser.TransactionQueue;
import moco.qiesbackend.parser.TransactionSummaryParser;
import moco.qiesbackend.record.TransactionRecord;

@Log
public class BackEndManager {
    TransactionQueue transactionSummary;
    CentralServicesList centralServicesList;
    Path centralServicesOutputPath;
    Path validServicesOutputPath;

    public BackEndManager(String mergedTransactionFile, String oldCentralFile, String newCentralFile, String newValidFile) {
        Path transactionSummaryPath = Paths.get(mergedTransactionFile);
        Path centralServicesPath = Paths.get(oldCentralFile);
        centralServicesOutputPath = Paths.get(newCentralFile);
        validServicesOutputPath = Paths.get(newValidFile);

        transactionSummary = TransactionSummaryParser.parseFile(transactionSummaryPath);
        centralServicesList = CentralServicesParser.parseFile(centralServicesPath);
    }

    public void operate() {
        TransactionRecord record;
        while(!transactionSummary.isEmpty()) {
            record = transactionSummary.pop();

            switch (record.getCode()) {
                case CRE:
                    processCRE(record);
                    break;
                case DEL:
                    processDEL(record);
                    break;
                case SEL:
                    processSEL(record);
                    break;
                case CAN:
                    processCAN(record); // Block testing start
                    break;
                case CHG:
                    processCHG(record);
                    break;
                case EOS:
                    break;
            }
        }
        centralServicesList.writeValidServicesFile(validServicesOutputPath);
        centralServicesList.writeCentralServicesFile(centralServicesOutputPath);
    }

    // Create service
    private void processCRE(TransactionRecord record) {
        String logMessage = "Failed to create new service. A service with that number already exists.";
        Level logLevel = Level.WARNING;
        if (!centralServicesList.contains(record.getSourceNumber())) {
            Service newService = new Service();
            newService.setServiceNumber(record.getSourceNumber());
            newService.setServiceName(record.getServiceName());
            centralServicesList.add(newService);
            logMessage = "New service created";
            logLevel = Level.INFO;
        }
        log.log(logLevel, logMessage);
    }

    // Delete service
    private void processDEL(TransactionRecord record) {
        String logMessage = "Failed to delete service. Service does not exist.";
        Level logLevel = Level.WARNING;
        if (centralServicesList.contains(record.getSourceNumber())) {
            Service toDelete = centralServicesList.get(record.getSourceNumber());
            logMessage = "Failed to delete service. Service still has sold tickets.";
            if (toDelete.getTicketsSold().getNumber() == 0) {
                centralServicesList.delete(toDelete.getServiceNumber());
                logMessage = "Service deleted";
                logLevel = Level.INFO;
            }
        }
        log.log(logLevel, logMessage);
    }

    // Sell tickets
    private void processSEL(TransactionRecord record) {
        String logMessage = "Failed to sell tickets. Service does not exist";
        Level logLevel = Level.WARNING;
        if (centralServicesList.contains(record.getSourceNumber())) {
            Service service = centralServicesList.get(record.getSourceNumber());
            try {
                service.addTickets(record.getNumberTickets().getNumber());
                logMessage = "Successfully sold tickets.";
                logLevel = Level.INFO;
            } catch (IllegalArgumentException e) {
                logMessage = "Failed to sell tickets. Number to sell will exceed service capacity";
            }
        }
        log.log(logLevel, logMessage);
    }

    // Cancel tickets
    private void processCAN(TransactionRecord record) {
        String logMessage = "Failed to cancel tickets. The service does not exist.";
        Level logLevel = Level.WARNING;
        if (centralServicesList.contains(record.getSourceNumber())) {
            Service service = centralServicesList.get(record.getSourceNumber());
            try {
                service.removeTickets(record.getNumberTickets().getNumber());
                logMessage = "Successfully cancelled tickets.";
                logLevel = Level.INFO;
            } catch (IllegalArgumentException e) {
                logMessage = "Failed to cancel tickets. Number to cancel exceeds number of tickets sold.";
            }
        }
        log.log(logLevel, logMessage);
    }

    // Change tickets
    private void processCHG(TransactionRecord record) {
        String logMessage = "Failed to change tickets. Source service does not exist.";
        Level logLevel = Level.WARNING;
        if (centralServicesList.contains(record.getSourceNumber())) {
            Service source = centralServicesList.get(record.getSourceNumber());
            logMessage = "Failed to change tickets. Destination service does not exist.";
            if (centralServicesList.contains(record.getDestinationNumber())) {
                Service destination = centralServicesList.get(record.getDestinationNumber());
                int transfer = record.getNumberTickets().getNumber();
                logMessage = "Failed to change tickets. Tickets to change exceeds number of tickets sold on source service.";
                if (transfer > source.getTicketsSold().getNumber()) {
                    logMessage = "Failed to change tickets. Tickets to change will exceed destination service capacity.";
                    if ((destination.getTicketsSold().getNumber() + transfer) > destination.getServiceCapacity()) {
                        source.removeTickets(transfer);
                        destination.addTickets(transfer);
                        logMessage = "Successfully changed tickets.";
                        logLevel = Level.INFO;
                    }
                }
            }
        }
        log.log(logLevel, logMessage);
    }
}