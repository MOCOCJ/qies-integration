package moco.qiesbackend.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;
import moco.qiesbackend.record.NumberTickets;
import moco.qiesbackend.record.ServiceDate;
import moco.qiesbackend.record.ServiceName;
import moco.qiesbackend.record.ServiceNumber;
import moco.qiesbackend.record.TransactionCode;
import moco.qiesbackend.record.TransactionRecord;

/**
 * TransactionSummaryParser
 */
@Log
public class TransactionSummaryParser {

    public static TransactionQueue parseFile(Path transactionSummaryPath) {
        TransactionQueue transactionQueue = new TransactionQueue();
        List<String> transactionFileLines = null;
        try {
            transactionFileLines = Files.readAllLines(transactionSummaryPath);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Unable to read merged transaction summary file");
            e.printStackTrace();
        }

        for (String line : transactionFileLines) {
            transactionQueue.push(parseLine(line));
        }

        return transactionQueue;
    }

    public static TransactionRecord parseLine(String transactionString) {
        String tempCode = transactionString.substring(0, 3);
        TransactionCode CODE;
        TransactionRecord newRecord;

        switch (tempCode) {
        case "CRE":
            CODE = TransactionCode.CRE;
            newRecord = new TransactionRecord(CODE);
            finalParse(transactionString, newRecord);
            return newRecord;
        case "DEL":
            CODE = TransactionCode.DEL;
            newRecord = new TransactionRecord(CODE);
            finalParse(transactionString, newRecord);
            return newRecord;
        case "SEL":
            CODE = TransactionCode.SEL;
            newRecord = new TransactionRecord(CODE);
            finalParse(transactionString, newRecord);
            return newRecord;
        case "CAN":
            CODE = TransactionCode.CAN;
            newRecord = new TransactionRecord(CODE);
            finalParse(transactionString, newRecord);
            return newRecord;
        case "CHG":
            CODE = TransactionCode.CHG;
            newRecord = new TransactionRecord(CODE);
            finalParse(transactionString, newRecord);
            return newRecord;
        case "EOS":
            CODE = TransactionCode.EOS;
            newRecord = new TransactionRecord(CODE);
            finalParse(transactionString, newRecord);
            return newRecord;
        default:
            break;
        }

        return null;
    }

    /**
     * Will parse the transaction string pulling apart the string into its various
     * components
     * 
     * @param transactionString
     * @param newRecord
     */
    private static void finalParse(String transactionString, TransactionRecord newRecord) {
        int parceLocation = 4, prevLocation = parceLocation, dateLength = 0;
        String temp = "";
        NumberTickets tempTickets;
        ServiceDate tempDate;
        ServiceName tempName;
        ServiceNumber tempNumber;

        // Source Number parse
        temp = transactionString.substring(parceLocation, parceLocation + 5);
        if (newRecord.getCode().toString().equals("EOS")) {
            tempNumber = new ServiceNumber(temp, true);
            newRecord.setSourceNumber(tempNumber);
        } else {
            tempNumber = new ServiceNumber(temp, false);
            newRecord.setSourceNumber(tempNumber);
        }
        parceLocation += 6;
        prevLocation = parceLocation;

        // Number Ticket parse
        while (transactionString.charAt(parceLocation) != ' ') {
            parceLocation++;
        }
        temp = transactionString.substring(prevLocation, parceLocation);
        if (temp.equals("0")) {
            tempTickets = new NumberTickets(temp, true);
        } else {
            tempTickets = new NumberTickets(temp, false);
        }
        newRecord.setNumberTickets(tempTickets);
        parceLocation++;
        prevLocation = parceLocation;

        // Destination Number parse
        temp = transactionString.substring(parceLocation, parceLocation + 5);
        if (temp.equals("00000")) {
            tempNumber = new ServiceNumber(temp, true);
            newRecord.setDestinationNumber(tempNumber);
        } else {
            tempNumber = new ServiceNumber(temp, false);
            newRecord.setDestinationNumber(tempNumber);
        }
        parceLocation += 6;
        prevLocation = parceLocation;
        parceLocation = transactionString.length() - 1;

        // Service Name parse
        while (transactionString.charAt(parceLocation) != ' ') {
            parceLocation--;
            dateLength++;
        }
        temp = transactionString.substring(prevLocation, transactionString.length() - dateLength - 1);
        tempName = new ServiceName(temp);
        newRecord.setServiceName(tempName);
        prevLocation = transactionString.length() - dateLength;

        // Service Date parse
        temp = transactionString.substring(prevLocation);
        if (temp.length() == 1 && temp.charAt(0) == '0') {
            tempDate = new ServiceDate(temp, true);
            newRecord.setServiceDate(tempDate);
        } else {
            tempDate = new ServiceDate(temp, false);
            newRecord.setServiceDate(tempDate);
        }
    }
}