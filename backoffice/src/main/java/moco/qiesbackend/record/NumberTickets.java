package moco.qiesbackend.record;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NumberTickets
 */
@Getter
@Setter
@NoArgsConstructor
public class NumberTickets extends RecordElement {
    // Vars
    private int number;
    private final static int DEFAULT = 0;
    private final static String NUMBERS = "0123456789";

    public NumberTickets(String numbers, boolean defaultStr) {
        if (!defaultStr) {
            if (isValid(numbers)) {
                number = Integer.parseInt(numbers);
                isSet = true;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static boolean isValid(String value) {
        int temp;
        if (!value.equals(null) && value.length() < 4) {
            for (int i = 0; i < value.length(); i++) {
                if (!NUMBERS.contains("" + value.charAt(i))) {
                    return false;
                }
            }

            temp = Integer.parseInt(value);
            if (temp >= 1 && temp <= 1000) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        if (isSet) {
            return Integer.toString(number);
        }
        return Integer.toString(DEFAULT);
    }

}