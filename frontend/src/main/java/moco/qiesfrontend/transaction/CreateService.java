package moco.qiesfrontend.transaction;

import java.util.Map;

import moco.qiesfrontend.session.Input;
import moco.qiesfrontend.session.SessionManager;
import moco.qiesfrontend.transaction.record.ServiceDate;
import moco.qiesfrontend.transaction.record.ServiceName;
import moco.qiesfrontend.transaction.record.ServiceNumber;
import moco.qiesfrontend.transaction.record.TransactionCode;
import moco.qiesfrontend.transaction.record.TransactionRecord;

/**
 * CreateService
 */
public class CreateService extends Transaction {
    // Vars
    public static TransactionCode CODE = TransactionCode.CRE;

    public CreateService() {
        record = new TransactionRecord(CODE);
    }

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager) {
        String serviceNumberIn;
        String serviceDateIn;
        String serviceNameIn;
        ServiceNumber serviceNumber;
        ServiceDate serviceDate;
        ServiceName serviceName;

        serviceNumberIn = input.takeInput("Enter service number of the service you wish to create.");
        try {
            serviceNumber = new ServiceNumber(serviceNumberIn);
            if (manager.isInValidServices(serviceNumber)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service number.");
            return null;
        }

        serviceDateIn = input.takeInput("Enter service date of the service you wish to create.");
        try {
            serviceDate = new ServiceDate(serviceDateIn);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service date.");
            return null;
        }

        serviceNameIn = input.takeInput("Enter service name of the service you wish to create.");
        try {
            serviceName = new ServiceName(serviceNameIn);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid service name.");
            return null;
        }

        System.out.format("Service %s created on %s with the name %s\n", serviceNumberIn, serviceDate.toString(),
                serviceNameIn);
        record.setSourceNumber(serviceNumber);
        record.setServiceDate(serviceDate);
        record.setServiceName(serviceName);

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

    @Override
    public TransactionRecord makeTransaction(Input input, SessionManager manager, int ticketCount,
            Map<String, Integer> canceledTickets) {
        return null;
    }

}
