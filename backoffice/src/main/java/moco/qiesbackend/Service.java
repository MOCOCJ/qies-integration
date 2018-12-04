package moco.qiesbackend;

import lombok.Getter;
import lombok.Setter;
import moco.qiesbackend.record.NumberTickets;
import moco.qiesbackend.record.ServiceName;
import moco.qiesbackend.record.ServiceNumber;

/**
 * Service
 */
@Getter
@Setter
public class Service {

    private ServiceNumber serviceNumber;
    private ServiceName serviceName;
    private TicketsSold ticketsSold;
    private int serviceCapacity; // TODO: wrap into its own type later

    public Service() {
        serviceCapacity = 30;
        serviceNumber = new ServiceNumber();
        serviceName = new ServiceName();
        ticketsSold = new TicketsSold();
    }

    public void addTickets(int toAdd) {
        int oldNum = ticketsSold.getNumber();
        int newNum = oldNum + toAdd;
        if (newNum > serviceCapacity) {
            throw new IllegalArgumentException();
        } else {
            String str = newNum + "";
            ticketsSold = new TicketsSold(str);
        }
    }

    public void removeTickets(int toRemove) {
        int oldNum = ticketsSold.getNumber();
        int newNum = oldNum - toRemove;
        if (newNum < 0) {
            throw new IllegalArgumentException();
        } else {
            String str = newNum + "";
            ticketsSold = new TicketsSold(str);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(serviceNumber);
        sb.append(" ");
        sb.append(serviceCapacity);
        sb.append(" ");
        sb.append(ticketsSold);
        sb.append(" ");
        sb.append(serviceName);

        return sb.toString();
    }
}