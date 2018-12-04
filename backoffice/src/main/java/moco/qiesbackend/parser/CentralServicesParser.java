package moco.qiesbackend.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;
import moco.qiesbackend.CentralServicesList;
import moco.qiesbackend.Service;
import moco.qiesbackend.TicketsSold;
import moco.qiesbackend.record.NumberTickets;
import moco.qiesbackend.record.ServiceName;
import moco.qiesbackend.record.ServiceNumber;

/**
 * CentralServicesParser
 */
@Log
public class CentralServicesParser {

    public static CentralServicesList parseFile(Path centralServicesPath) {
        CentralServicesList centralServicesList = new CentralServicesList();
        List<String> servicesFileLines = null;

        try {
            servicesFileLines = Files.readAllLines(centralServicesPath);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Unable to read old central services file");
            e.printStackTrace();
        }

        for (String line : servicesFileLines) {
            centralServicesList.add(parseLine(line));
        }

        return centralServicesList;
    }

    public static Service parseLine(String serviceString) {
        Service service = new Service();
        String serviceTokens[] = serviceString.split(" ", 4);

        service.setServiceNumber(new ServiceNumber(serviceTokens[0], false));
        service.setServiceCapacity(Integer.parseInt(serviceTokens[1]));
        service.setTicketsSold(new TicketsSold(serviceTokens[2]));
        service.setServiceName(new ServiceName(serviceTokens[3]));

        return service;
    }
}