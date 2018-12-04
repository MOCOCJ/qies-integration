package moco.qiesbackend.record;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * TransactionRecord
 */
@Getter
@Setter
@EqualsAndHashCode
public class TransactionRecord {
    // Vars
    private TransactionCode code;
    private ServiceNumber sourceNumber;
    private NumberTickets numberTickets;
    private ServiceNumber destinationNumber;
    private ServiceName serviceName;
    private ServiceDate serviceDate;

    public TransactionRecord(TransactionCode code) {
        this.code = code;
        this.sourceNumber = new ServiceNumber();
        this.numberTickets = new NumberTickets();
        this.destinationNumber = new ServiceNumber();
        this.serviceName = new ServiceName();
        this.serviceDate = new ServiceDate();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(69);

        sb.append(code.toString());
        sb.append(' ');
        sb.append(sourceNumber.toString());
        sb.append(' ');
        sb.append(numberTickets.toString());
        sb.append(' ');
        sb.append(destinationNumber.toString());
        sb.append(' ');
        sb.append(serviceName.toString());
        sb.append(' ');
        sb.append(serviceDate.toString());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
    
}