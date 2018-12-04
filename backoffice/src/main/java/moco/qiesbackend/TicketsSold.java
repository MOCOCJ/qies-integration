package moco.qiesbackend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TicketsSold
 */
@Getter
@Setter
@NoArgsConstructor
public class TicketsSold {

    private int number;

    public TicketsSold(String number) {
        if (isValid(Integer.parseInt(number))) {
            this.number = Integer.parseInt(number);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static boolean isValid(int number) {
        return (number >= 0 && number <= 9999);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}